package com.ordermanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orderItems")
public class OrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderItemId;

	@Column(name = "productId")
	private int productId;
	
	@NotBlank(message = "ProductName id is mandatory")
	@Column(name = "productName")
	private String productName;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "quantity")
	private double quantity;
	
	

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false,cascade = CascadeType.PERSIST)
	@JoinColumn(name = "orderId", nullable = false)
	private Orders orders;

	public OrderItems() {
		super();
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItems [orderItemId=" + orderItemId + ", productId=" + productId + ", productName=" + productName
				+ ", price=" + price + ", quantity=" + quantity + ", orders=" + orders + ", getOrderItemId()="
				+ getOrderItemId() + ", getOrders()=" + getOrders() + ", getProductId()=" + getProductId()
				+ ", getProductName()=" + getProductName() + ", getPrice()=" + getPrice() + ", getQuantity()="
				+ getQuantity() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	

}
