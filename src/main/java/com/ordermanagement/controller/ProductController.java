package com.ordermanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.entity.Products;
import com.ordermanagement.entity.User;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.service.ProductService;

@CrossOrigin
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	
	// To get all products
	@GetMapping(value = "/allProducts")
	public List<Products> getAllProducts() {
		List<Products> allProducts = new ArrayList<Products>();
		
		try {
			return productService.getAllProducts();
		} catch (Exception e) {
			
			e.printStackTrace();
			return  allProducts;
		}
	}


	@GetMapping("/getproduct/{id}")
	public ResponseEntity<Products> getproductById(@PathVariable int productId) {
		
		Products product = new Products();
		try {
			product = productService.getproductById(productId)
					.orElseThrow(() -> new ResourceNotFoundException
							("USer is not exist with id : " +productId));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok(product);
	}
	
	
}
