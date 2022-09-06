package com.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordermanagement.entity.OrderItems;

public interface OrderItemsDao extends JpaRepository<OrderItems, Integer> {

}
