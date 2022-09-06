package com.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication

public class RetailOrderManagementSystemApplication {
	
	private static final Logger logger = LogManager.getLogger(RetailOrderManagementSystemApplication.class);

	public static void main(String[] args) {
		
		SpringApplication.run(RetailOrderManagementSystemApplication.class, args);
		
		logger.info("RetailOrderManagementSystemApplication Application started successfully ");

	}

}
