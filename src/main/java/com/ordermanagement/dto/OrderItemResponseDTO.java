package com.ordermanagement.dto;

public class OrderItemResponseDTO {

	private int orderItemsId;

	private String orderItemsStatus;

	public int getOrderItemsId() {
		return orderItemsId;
	}

	public void setOrderItemsId(int orderItemsId) {
		this.orderItemsId = orderItemsId;
	}

	public String getOrderItemsStatus() {
		return orderItemsStatus;
	}

	public void setOrderItemsStatus(String orderItemsStatus) {
		this.orderItemsStatus = orderItemsStatus;
	}

}
