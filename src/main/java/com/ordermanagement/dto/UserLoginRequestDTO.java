package com.ordermanagement.dto;

public class UserLoginRequestDTO {

	
	private String emailId;
	
	private String passWord;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public UserLoginRequestDTO(String emailId, String passWord) {
		super();
		this.emailId = emailId;
		this.passWord = passWord;
	}

	public UserLoginRequestDTO() {
		super();
	}
	
	
	
}
