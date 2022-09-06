package com.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dto.LoginResponseDTO;
import com.ordermanagement.dto.UserLoginRequestDTO;
import com.ordermanagement.entity.User;
import com.ordermanagement.exception.ResourceNotFoundException;
import com.ordermanagement.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin
@RestController
public class UserController {
	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	
	// To get user by Id

	@GetMapping("/getuser/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		logger.info("calling getuser in  controller with user id : " + id);
		User user = userService.getUserById(id)
				.orElseThrow(() -> new ResourceNotFoundException("USer is not exist with id : " + id));

		logger.info("calling UserService with method getUserById from controller completed with id : " + id);

		return ResponseEntity.ok(user);
	}

	// To login user
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody UserLoginRequestDTO userLoginRequest) {

		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

		// validating Request Data with email id and Password
		if (userLoginRequest.getEmailId() == null || userLoginRequest.equals("")
				|| userLoginRequest.getPassWord() == null || userLoginRequest.getPassWord().equals("")) {

			logger.warn("calling loginUser in  controller with empty EmailId or empty password");

			loginResponseDTO.setMessage("Please Enter valid EmailID and Password");
			return new ResponseEntity<>(loginResponseDTO, HttpStatus.BAD_REQUEST);
		}

		User user = new User();

		// Get user data from service
		try {
			user = userService.loginUser(userLoginRequest);
		} catch (Exception e) {
			logger.error(" loginUser  has issue with emailId " + userLoginRequest.getEmailId() + "" + e.getMessage());
		}

		// building login sucess response
		loginResponseDTO.setUserName(user.getFirstName() + " " + user.getLastName());
		loginResponseDTO.setMessage("Login Success");
		loginResponseDTO.setUserId(user.getUserId());
		loginResponseDTO.setPassword(user.getPassword());
		loginResponseDTO.setEmailId(user.getEmailId());

		logger.info("user logged in successfully with email id " + user.getEmailId());

		return new ResponseEntity<LoginResponseDTO>(loginResponseDTO, HttpStatus.OK);
	}
	
	// to get all users
		@GetMapping(value = "/allusers")
		public List<User> getAllUsers() {
			logger.info("calling getAllUsers in  controller");
			return userService.getAllUsers();
		}

	// Register and save User

	@PostMapping("/register")
	public ResponseEntity<LoginResponseDTO> register(@RequestBody User user) {

		LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

		if (user.getEmailId() == null && user.getEmailId().equalsIgnoreCase("") && user.getPassword() == null
				&& user.getPassword().equalsIgnoreCase("")) {
			loginResponseDTO.setMessage("Please Enter valid EmailID and Password");
			return new ResponseEntity<>(loginResponseDTO, HttpStatus.BAD_REQUEST);
		}

		User resgisterdUser = new User();
		try {
			resgisterdUser = userService.save(user);
		} catch (Exception exception) {
			logger.error("Error while resgisterd user with email id  " + user.getEmailId());
		}

		loginResponseDTO.setUserName(resgisterdUser.getLastName());
		loginResponseDTO.setMessage("Login Success");
		loginResponseDTO.setUserId(resgisterdUser.getUserId());

		logger.info("user registered  successfully with email id " + resgisterdUser.getEmailId());

		return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
	}

}
