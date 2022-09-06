package com.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordermanagement.entity.Orders;

public interface OrdersDao extends JpaRepository<Orders, Integer>{

//	Orders getOrdersByUserId(int id);

}
