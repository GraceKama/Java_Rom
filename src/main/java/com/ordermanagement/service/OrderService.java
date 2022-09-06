package com.ordermanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dao.OrdersDao;
import com.ordermanagement.dao.UserDao;
import com.ordermanagement.dto.CreatedOrderRequestDTO;
import com.ordermanagement.dto.UpdateOrderRequestDTO;
import com.ordermanagement.entity.OrderItems;
import com.ordermanagement.entity.Orders;
import com.ordermanagement.entity.Products;
import com.ordermanagement.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class OrderService {
	private static final Logger logger = LogManager.getLogger(OrderService.class);

	@Autowired
	OrdersDao ordersDao;

	@Autowired
	UserDao userDao;
	

	public Set<Orders> getOrdersByUserId(int userid) throws Exception {
		Set<Orders> orders = new HashSet<>();
		
		try{
			User user = userDao.getById(userid);
		
		if (user != null) {
			orders = user.getOrders();
		}
		}catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while getOrdersByUserId " + userid + jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while getOrdersByUserId " + userid+ exception.getMessage());
			throw new Exception();
		}
				
		return orders;
	}

	// Delete Order 
	public void deleteOrderById(int orderid) throws Exception{
		try {
			ordersDao.deleteById(orderid);
		} catch (ConstraintViolationException constraintViolationException)  {
			logger.error(" ConstraintViolationException  while delete orders " + orderid + constraintViolationException.getMessage());
			throw new Exception();
		}
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while delete orders " + orderid + jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while delete orders " + orderid + exception.getMessage());
			throw new Exception();
		}
	}
	
	

	//Create Order
	public Orders createOrder(CreatedOrderRequestDTO createdOrderRequestDTO)  throws Exception{
		Orders orders = new Orders();
		double orderPrice = 0;
		
		try {
		//Check the user id exist in database
		User user = userDao.getById(createdOrderRequestDTO.getUserId());
		
		user.setUserId(createdOrderRequestDTO.getUserId());
		
		//Java8  Feature to remove the products quantity less than 1
				List<Products> productsToSave = createdOrderRequestDTO.getProducts()
		                .stream()
		                .filter(product -> product.getQuantity() >=1)
		                .collect(Collectors.toList());
				
				
		// build Orders Object to save
		orders.setUser(user);
		orders.setOrderDate(new Date());
		Orders savedOrder = ordersDao.save(orders);
	
		Set<OrderItems> orderItemsSet = new HashSet<OrderItems>();
	
		for (Products product : productsToSave) {
			OrderItems OrderItems = new OrderItems();
			OrderItems.setProductId(product.getProductId());
			OrderItems.setProductName(product.getProductName());
			OrderItems.setPrice(product.getPrice());
			OrderItems.setQuantity(product.getQuantity());
			OrderItems.setOrders(savedOrder);
			orderPrice = orderPrice + (product.getPrice() * product.getQuantity());
			orderItemsSet.add(OrderItems);
	   }
	
		savedOrder.setOrderItems(orderItemsSet);
		savedOrder.setOrderPrice(orderPrice);
		
		return ordersDao.save(savedOrder);
		}
		catch (ConstraintViolationException constraintViolationException)  {
			logger.error(" ConstraintViolationException  while create orders " + createdOrderRequestDTO.toString() + constraintViolationException.getMessage());
			throw new Exception();
		}
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while create orders " + createdOrderRequestDTO.toString()+ jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while create orders " + createdOrderRequestDTO.toString() + exception.getMessage());
			throw new Exception();
		}

	}

	public void updateOrder(UpdateOrderRequestDTO updateOrderRequestDTO) throws Exception {
		
		double orderPrice=0;
		
		try {
		Orders orders = ordersDao.getById(updateOrderRequestDTO.getOrderId());
			
		List<OrderItems> orderItemsToUpdate = updateOrderRequestDTO.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getQuantity() >=1)
                .collect(Collectors.toList());
	
		Set<OrderItems> orderItemsToupdate = new HashSet<>(orderItemsToUpdate) ;
		
		for(OrderItems orderItem: orderItemsToupdate) {
			orderPrice = orderPrice + (orderItem.getPrice() * orderItem.getQuantity());
			orderItem.setPrice(orderItem.getPrice() * orderItem.getQuantity());
			orderItem.setOrders(orders);
		}
		
		orders.setOrderPrice(orderPrice);
		
		orders.setOrderItems(orderItemsToupdate);
		ordersDao.save(orders);
		
		} 
		
		catch (ConstraintViolationException constraintViolationException)  {
			logger.error(" ConstraintViolationException  while update orders " + updateOrderRequestDTO.toString() + constraintViolationException.getMessage());
			throw new Exception();
		}
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while update orders " + updateOrderRequestDTO.toString()+ jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while update orders " + updateOrderRequestDTO.toString() + exception.getMessage());
			throw new Exception();
		}
		
		
	}
	
	//added this method  to get the all orders
	public List<Orders> getAllOrders() throws Exception {
		List<Orders>  orderList = new ArrayList<Orders>();
	
		try {	
		   ordersDao.findAll();
		} 
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while getting all orders " + jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while getting all orders "  + exception.getMessage());
			throw new Exception();
		}
		
		return orderList;
	}

}
