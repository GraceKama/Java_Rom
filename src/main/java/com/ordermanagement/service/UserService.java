package com.ordermanagement.service;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.controller.UserController;
import com.ordermanagement.dao.UserDao;
import com.ordermanagement.dto.UserLoginRequestDTO;
import com.ordermanagement.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;

@Service
public class UserService {

	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	//get all users
	public List<User> getAllUsers()  {
		logger.info(" getAllUsers() in UserDao " );
		return userDao.findAll();
	}

	
	//get user by id
	public Optional<User> getUserById(int id) {
	    logger.info(" getUserById() in UserDao with user id " + id );
		return userDao.findById(id);
	}

	public User save(User user)  throws Exception{
		logger.info(" save() in UserDao with user  email id " + user.getEmailId() );
		User savedUser = new User();
		
		try{
			userDao.save(user) ;
		}
		catch (ConstraintViolationException constraintViolationException)  {
			logger.error(" ConstraintViolationException  with user  email id while register " + user.getEmailId() + constraintViolationException.getMessage());
			throw new Exception();
		}
		catch (JDBCException  jdbcException)  {
			logger.error(" JDBCException  with user  email id while register " + user.getEmailId() + jdbcException.getMessage());
			throw new Exception();
		}
		
		catch (Exception exception ) {
			logger.error(" exception  with user  email id while register " + user.getEmailId() + exception.getMessage());
			throw new Exception();
		}
		
		return savedUser;
	}

	public User loginUser(UserLoginRequestDTO userLoginRequest) throws Exception {

		logger.info(" loginUser() in UserService with user  email id " + userLoginRequest.getEmailId());
		User user = new User();
		try {
			user = userDao.findByEmailIdAndPassWord(userLoginRequest.getEmailId(), userLoginRequest.getPassWord());
		} catch (NoSuchElementException noSuchElementException) {
			logger.error(" NoSuchElementException  with user  email id while login " + userLoginRequest.getEmailId()
					+ noSuchElementException.getMessage());
			throw new Exception();
		} catch (JDBCException jdbcException) {
			logger.error(" JDBCException  with user  email id while login " + userLoginRequest.getEmailId()
					+ jdbcException.getMessage());
			throw new Exception();
		}

		catch (Exception exception) {
			logger.error(" exception  with user  email id while login " + userLoginRequest.getEmailId()
					+ exception.getMessage());
			throw new Exception();
		}

		logger.info(" after calling loginUser() in UserService with user  email id " + userLoginRequest.getEmailId());

		return user;
	}

	

}
