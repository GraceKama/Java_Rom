package com.ordermanagement.dto;

import java.util.List;

import com.ordermanagement.entity.OrderItems;

public class UpdateOrderRequestDTO {
	
	private int orderId;
	
	private String emailId;
	
	private String password;
	
	private List<OrderItems> orderItems ;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public List<OrderItems> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
	

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UpdateOrderRequestDTO [orderId=" + orderId + ", emailId=" + emailId + 
				 ", orderItems=" + orderItems + ", getOrderId()=" + getOrderId() + ", getOrderItems()="
				+ getOrderItems() + ", getEmailId()=" + getEmailId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
	
	
	

}
