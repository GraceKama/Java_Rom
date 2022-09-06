package com.ordermanagement.dto;

public class DeleteOrdersRequestDTO {

	private String emailId;
	private String password;
	private int orderId;
	private int  orderItemId;


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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Override
	public String toString() {
		return "DeleteOrdersRequestDTO [emailId=" + emailId + ", password=" + password + ", orderId=" + orderId
				+ ", orderItemId=" + orderItemId + "]";
	}

	
}
