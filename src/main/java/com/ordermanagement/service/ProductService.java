package com.ordermanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dao.ProductsDao;
import com.ordermanagement.entity.Products;
import com.ordermanagement.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ProductService {

	private static final Logger logger = LogManager.getLogger(OrderService.class);

	@Autowired
	ProductsDao productsDao;

	public List<Products> getAllProducts() throws Exception {
		List<Products> listProducts = new ArrayList<>();
		try {
			listProducts = productsDao.findAll();
		} catch (JDBCException jdbcException) {
			logger.error(" JDBCException  while getAllProducts " + jdbcException.getMessage());
			throw new Exception();
		}

		catch (Exception exception) {
			logger.error(" Exception   while getAllProducts " + exception.getMessage());
			throw new Exception();
		}

		return listProducts;
	}

	public Optional<Products> getproductById(int productId) throws Exception {

		Optional<Products> prodcuts;

		try {
			prodcuts = productsDao.findById(productId);
		} catch (JDBCException jdbcException) {
			logger.error(" JDBCException  while getproductById " + productId + jdbcException.getMessage());
			throw new Exception();
		}

		catch (Exception exception) {
			logger.error(" Exception  while getproductById " + productId + exception.getMessage());
			throw new Exception();
		}

		return prodcuts;
	}

}
