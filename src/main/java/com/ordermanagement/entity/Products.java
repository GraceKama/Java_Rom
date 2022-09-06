package com.ordermanagement.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(name = "product_name", unique = true)
	private String productName;

	@Column(name = "price")
	private double price;
	
	private double quantity;
	
	//boolean productSelected;

	public Products() {
		super();
	}

	public Products(int productId, String productName, double price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
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
		return "Products [productId=" + productId + ", productName=" + productName + ", price=" + price + ", quantity="
				+ quantity + ", getProductId()=" + getProductId() + ", getProductName()=" + getProductName()
				+ ", getPrice()=" + getPrice() + ", getQuantity()=" + getQuantity() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, productId, productName, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		return Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && productId == other.productId
				&& Objects.equals(productName, other.productName)
				&& Double.doubleToLongBits(quantity) == Double.doubleToLongBits(other.quantity);
	}
	
	

	
	

}
