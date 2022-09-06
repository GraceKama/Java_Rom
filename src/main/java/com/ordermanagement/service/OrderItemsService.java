package com.ordermanagement.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dao.OrderItemsDao;
import com.ordermanagement.dao.OrdersDao;
import com.ordermanagement.entity.OrderItems;
import com.ordermanagement.entity.Orders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class OrderItemsService {
	
	private static final Logger logger = LogManager.getLogger(OrderService.class);
	
	@Autowired 
	OrdersDao ordersDao;
	
	@Autowired 
	OrderItemsDao orderItemsDao;
	
	
	public Set<OrderItems> getOrderItems(int orderId) throws Exception {
		Set<OrderItems> orderItems = new HashSet<OrderItems>();

		try {
			Orders order = ordersDao.getById(orderId);
			if (order != null) {
				orderItems = order.getOrderItems();
			}
		} catch (JDBCException jdbcException) {
			logger.error(" JDBCException  while getOrderItems " + orderId + jdbcException.getMessage());
			throw new Exception();
		}

		catch (Exception exception) {
			logger.error(" Exception  while delete getOrderItems" + orderId + exception.getMessage());
			throw new Exception();
		}

		return orderItems;
	}
	
	
	public void deleteOrderItemById(int orderItemId, int orderId) throws Exception {
		try {
			// Delete Order Item
			orderItemsDao.deleteById(orderItemId);
			
			// Recalculate Order Price
			double orderPrice =0;
			 Orders order = ordersDao.findById(orderId).get();
			 for(OrderItems orderItem: order.getOrderItems()) {
					orderPrice = orderPrice + (orderItem.getPrice() * orderItem.getQuantity());
				}
			   order.setOrderPrice(orderPrice);
			   ordersDao.save(order);
			
		}catch (ConstraintViolationException constraintViolationException)  {
			logger.error(" ConstraintViolationException  while delete order item " + orderItemId + constraintViolationException.getMessage());
			throw new Exception();
		}
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  while delete order item " + orderItemId + jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" Exception  while delete order item " + orderItemId + exception.getMessage());
			throw new Exception();
		}
		
	}
}
