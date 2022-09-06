package com.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordermanagement.entity.Products;

public interface ProductsDao extends JpaRepository<Products, Integer>{

}
