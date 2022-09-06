package com.ordermanagement.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ordermanagement.entity.Products;

public class CreatedOrderRequestDTO {

		
	private int userId;

	private List<Products> products = new ArrayList();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}
	
	@Override
	public String toString() {
		return "CreatedOrderRequestDTO [userId=" + userId + ", products=" + products + ", getUserId()=" + getUserId()
				+ ", getProducts()=" + getProducts() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(products, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreatedOrderRequestDTO other = (CreatedOrderRequestDTO) obj;
		return Objects.equals(products, other.products) && userId == other.userId;
	}
	
	

}
