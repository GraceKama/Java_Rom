package com.ordermanagement.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@Column(name = "orderDate")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date orderDate;

	@Column(name = "orderPrice")
	private double orderPrice;

	@OneToMany(mappedBy = "orders", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OrderItems> orderItems;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false,cascade 
	        = CascadeType.PERSIST)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<OrderItems> getOrderItems() {
		return orderItems;
	}

	public Orders() {
		super();
	}

	public void setOrderItems(Set<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", orderDate=" + orderDate + ", orderPrice=" + orderPrice
				+ ", orderItems=" + orderItems + ", user=" + user + ", getOrderId()=" + getOrderId()
				+ ", getOrderDate()=" + getOrderDate() + ", getUser()=" + getUser() + ", getOrderItems()="
				+ getOrderItems() + ", getOrderPrice()=" + getOrderPrice() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
