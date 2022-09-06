package com.ordermanagement.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dao.OrderItemsDao;
import com.ordermanagement.dto.DeleteOrdersRequestDTO;
import com.ordermanagement.dto.OrderItemResponseDTO;
import com.ordermanagement.dto.OrderResponseDTO;
import com.ordermanagement.dto.UserLoginRequestDTO;
import com.ordermanagement.entity.OrderItems;
import com.ordermanagement.entity.Orders;
import com.ordermanagement.entity.User;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.service.OrderItemsService;
import com.ordermanagement.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

@CrossOrigin
@RestController
public class OrderItemsController {
	
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	@Autowired
	OrderItemsService orderItemsService;
	
	@Autowired
	OrderItemsDao orderItemsDao;
	
	@Autowired
	private UserService userService;

	@GetMapping(value = "/getordersitems/{orderId}")
	public List<OrderItems> getOrderItems(@PathVariable int orderId) {
		List<OrderItems>  orderItems = new ArrayList<>();
		
		try {
			 orderItems.addAll(orderItemsService.getOrderItems(orderId));
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return orderItems;
	}
	
	
	
	// Use this method when we delete with authenticate., Comment above method once change in Angular.
	@PostMapping(value = "/deleteorderitem")
	public ResponseEntity<OrderItemResponseDTO> deleteOrderItemWithAuthnitacte(
			@RequestBody DeleteOrdersRequestDTO deleteOrdersRequestDTO) {

		OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
		
		int orderItemId = deleteOrdersRequestDTO.getOrderItemId();
		
		User user = new User();
		// Authenticate user
		try {
			UserLoginRequestDTO userLoginRequest = new UserLoginRequestDTO();
			userLoginRequest.setEmailId(deleteOrdersRequestDTO.getEmailId());
			userLoginRequest.setPassWord(deleteOrdersRequestDTO.getPassword());
			user = userService.loginUser(userLoginRequest);
		} catch (Exception exception) {
			logger.error(" loginUser  has issue with emailId " + orderItemId + "" + exception.getMessage());
			orderItemResponseDTO.setOrderItemsId(deleteOrdersRequestDTO.getOrderId());
			orderItemResponseDTO.setOrderItemsStatus(
					"Order Item Could not delete authentication failed while delete " + orderItemId);
			logger.error(" Order Could not delete authentication failed while delete :  " + orderItemId);
			return new ResponseEntity<OrderItemResponseDTO>(orderItemResponseDTO, HttpStatus.OK);
		}

		if(user!=null && user.getEmailId() !=null && !user.getEmailId().equalsIgnoreCase("") )
		{
			// Delete Order item process starts,After authenticate success
		
			try {
				orderItemsService.deleteOrderItemById(orderItemId,deleteOrdersRequestDTO.getOrderId());
			} catch (Exception e) {
				orderItemResponseDTO.setOrderItemsId(orderItemId);
				orderItemResponseDTO.setOrderItemsStatus("Order Item not deleted ");
			}

			orderItemResponseDTO.setOrderItemsId(orderItemId);
			orderItemResponseDTO.setOrderItemsStatus("Order Item Deleted ");
			logger.info("Order Item Deleted  with OrderItem :  " + orderItemId);

		}
		
		else {
			// authenticate failed
			orderItemResponseDTO.setOrderItemsId(orderItemId);
			orderItemResponseDTO
					.setOrderItemsStatus("Order Item Could not delete authentication failed while delete :  " + orderItemId);
		}

		return new ResponseEntity<OrderItemResponseDTO>(orderItemResponseDTO, HttpStatus.OK);
	}
	
	
	/*
	 * @DeleteMapping(value = "/deleteorderitem/{orderItemId}") public
	 * ResponseEntity<OrderItemResponseDTO> deleteOrder(@PathVariable int
	 * orderItemId) {
	 * 
	 * orderItemsDao.findById(orderItemId) .orElseThrow(() -> new
	 * ResourceNotFoundException ("Order Item not exist with id : " + orderItemId));
	 * 
	 * 
	 * OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO();
	 * 
	 * try { orderItemsService.deleteOrderItemById(orderItemId); } catch (Exception
	 * exception) { orderItemResponseDTO.setOrderItemsId(orderItemId);
	 * orderItemResponseDTO.setOrderItemsStatus("Order Item not deleted ");
	 * logger.error(" Delete order Item has error while delete order item " +
	 * orderItemId + "" + exception.getMessage()); }
	 * 
	 * orderItemResponseDTO.setOrderItemsId(orderItemId);
	 * orderItemResponseDTO.setOrderItemsStatus("Order Item Deleted Successfully ");
	 * 
	 * return new ResponseEntity(orderItemResponseDTO, HttpStatus.OK); }
	 */
	
}
