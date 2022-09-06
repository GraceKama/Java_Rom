package com.ordermanagement.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dao.OrdersDao;
import com.ordermanagement.dto.CreatedOrderRequestDTO;
import com.ordermanagement.dto.DeleteOrdersRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;
import com.ordermanagement.dto.UpdateOrderRequestDTO;
import com.ordermanagement.dto.UserLoginRequestDTO;
import com.ordermanagement.entity.Orders;
import com.ordermanagement.entity.User;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.service.OrderService;
import com.ordermanagement.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin
@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrdersDao ordersDao;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	
	//Get Orders by Id
	@GetMapping(value = "/getorders/{userid}")
	public Set<Orders> getOrdersByUserId(@PathVariable int userid) {
	
		logger.info(" Calling getOrdersByUserId " + userid );
		
		Set<Orders> orders = new HashSet<>();
		
		try {
			orders = orderService.getOrdersByUserId(userid);
		}
		catch(Exception exception ) {
			logger.error(" Calling getOrdersByUserId " + userid  + " " + exception.getMessage() );
						
		}
		
		return orders;
	}
	
	
	//Create Order
	@PostMapping(value="/createorder")
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreatedOrderRequestDTO createdOrderRequestDTO) {
		int orderId=0;
		
		logger.info(" Calling create Order with details " + createdOrderRequestDTO.toString() );
		
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		
		// If no products in order return 
		if(createdOrderRequestDTO.getProducts().size() <=0 || createdOrderRequestDTO.getProducts().isEmpty() ) {
			
			orderResponseDTO.setOrderStatus("Please Select Prodcuts to Create Order");
			logger.warn(" Trying to create order without any products " + createdOrderRequestDTO.toString() );
			
			return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
		}
		
		//Creating Order 
		try {
			Orders order= orderService.createOrder(createdOrderRequestDTO);
			orderId = order.getOrderId();
		}
		// if any exception while creating order like Database down,  or  inserting data in table
		catch( Exception e)  {
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus("Order not created successfully, Please contact the Stop shop.");
			logger.error(" Error while creating order with the details : " + createdOrderRequestDTO.toString() );
			return new ResponseEntity<>(orderResponseDTO, HttpStatus.BAD_REQUEST);
		}
		
		orderResponseDTO.setOrderId(orderId);
		orderResponseDTO.setOrderStatus("Order created successfully");
		logger.info(" Order created Successfully with the details : " + createdOrderRequestDTO.toString() );
		return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
	}
	
	
	//To get all Orders
    @GetMapping(value = "/allorders")
	public List<Orders> getOrders() {
		
    	logger.info(" calling  allorders");
		List<Orders> allOrders = new ArrayList<>();
		
		try {
			allOrders = orderService.getAllOrders();
		} catch (Exception exception) {
			logger.error(" Error while calling  allorders" + exception.getStackTrace());
		}
		return allOrders;
	}


	//To Update Order Items
	@PostMapping(value = "/updateOrderItems")
	public ResponseEntity<OrderResponseDTO> updateOrderItems(@RequestBody UpdateOrderRequestDTO updateOrderRequestDTO) {
		int orderId = updateOrderRequestDTO.getOrderId();
		
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		User user = new User();
		
		try {
			UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
			userLoginRequestDTO.setEmailId(updateOrderRequestDTO.getEmailId());
			userLoginRequestDTO.setPassWord(updateOrderRequestDTO.getPassword());
			 user = userService.loginUser(userLoginRequestDTO);
		}catch (Exception e) {
			logger.error(" loginUser  has issue with emailId " + orderId + "" + e.getMessage());
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus("Order Could not update authentication failed while update "  + orderId);
			logger.error(" Order Could not update authentication failed while update :  " + orderId);
			return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
		}
		
		if(user != null && user.getEmailId() !=null && !user.getEmailId().equalsIgnoreCase(""))
		{
			try {
				
				orderService.updateOrder(updateOrderRequestDTO);
				
				//constructing update order response 
				orderResponseDTO.setOrderId(updateOrderRequestDTO.getOrderId());
				orderResponseDTO.setOrderStatus("Order updated successfully ");
			  } 
			
			catch (Exception e) {
				logger.error(" Error while calling  updateOrderItems with details " + updateOrderRequestDTO.toString() + e.getMessage());
				orderResponseDTO.setOrderStatus("Could not update Order Items ");
			}
		}
		
		else {
			orderResponseDTO.setOrderId(updateOrderRequestDTO.getOrderId());
			orderResponseDTO.setOrderStatus("Order could not updated , because authentication failed ");
		}
		
		
		return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
		
	}
	
	
	
	//  this method when we delete with authenticate.
	@PostMapping(value = "/deleteorder")
	public ResponseEntity<OrderResponseDTO> deleteOrderWithAuthnitacte(@RequestBody DeleteOrdersRequestDTO deleteOrdersRequestDTO) {
		OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
		int orderId= deleteOrdersRequestDTO.getOrderId();
		User user  = new User();
		
			
		// Authenticate user 
		try {
			UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();
			userLoginRequestDTO.setEmailId(deleteOrdersRequestDTO.getEmailId());
			userLoginRequestDTO.setPassWord(deleteOrdersRequestDTO.getPassword());
			user = userService.loginUser(userLoginRequestDTO);
		} catch (Exception e) {
			logger.error(" loginUser  has issue with emailId " + orderId + "" + e.getMessage());
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus("Order Could not delete authentication failed while delete "  + orderId);
			logger.error(" Order Could not delete authentication failed while delete :  " + orderId);
			return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
		}
		
		if(user!=null && user.getEmailId() !=null && !user.getEmailId().equalsIgnoreCase("") )
		// Delete Order process starts,After authenticate  success.
		{	
			//int orderid = deleteOrdersRequestDTO.getOrderId();
			Orders orders = ordersDao.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException
						("Order not exist with id : " + orderId));
		if(orders.getOrderItems().size() > 1 ) {
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus("Order Could not delete as it has more than one orderItem.");
			logger.warn(" Order could not delete due to order contains more than   orderItem  with orderid :  " + orderId);
			return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
		}
		try {
			orderService.deleteOrderById(orderId);
		} catch (Exception exception) {
			logger.error(" Exception while deleting  orders  :  " + orderId + exception.getMessage());
		}
		orderResponseDTO.setOrderId(orderId);
		orderResponseDTO.setOrderStatus("Order Deleted  with order id " + orderId);
		logger.info("Order Deleted  with orderid :  " + orderId);
		
		}
	   // authenticate  failed and Order not deleted 
		else {
			orderResponseDTO.setOrderId(orderId);
			orderResponseDTO.setOrderStatus("Order Could not delete authentication failed while delete order :  " + orderId);
		}
		
		return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
	}


	
	
	
	
	//To delete order by Id
		//@DeleteMapping(value = "/deleteorder/{orderid}")
		public ResponseEntity<OrderResponseDTO> deleteOrder(@PathVariable int orderid) {
			OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
			
			Orders orders = ordersDao.findById(orderid)
					.orElseThrow(() -> new ResourceNotFoundException
							("Order not exist with id : " + orderid));
			
			if(orders.getOrderItems().size() > 1 ) {
				orderResponseDTO.setOrderId(orderid);
				orderResponseDTO.setOrderStatus("Order Could not delete as it has more than one orderItem.");
				logger.warn(" Order could not delete due to order contains more than   orderItem  with orderid :  " + orderid);
				return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
			}
			
			try {
				orderService.deleteOrderById(orderid);
			} catch (Exception exception) {
				logger.error(" Exception while deleting  orders  :  " + orderid + exception.getMessage());
			}
			orderResponseDTO.setOrderId(orderid);
			orderResponseDTO.setOrderStatus("Order Deleted ");
			logger.info("Order Deleted  with orderid :  " + orderid);
			
			return new ResponseEntity<OrderResponseDTO>(orderResponseDTO, HttpStatus.OK);
		}
		
}
