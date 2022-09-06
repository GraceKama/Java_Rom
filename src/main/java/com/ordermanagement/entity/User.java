package com.ordermanagement.entity;
//FetchType.LAZY means do not load the data till we call the getOrders methods, 
//CascadeType.ALL which is for all operations on ORDERS
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@NotBlank(message = "First Name is mandatory")
	@Column(name = "first_name")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	@Column(name = "last_name")
	private String lastName;
	
    @Email
	@NotBlank(message = "Email Id is mandatory")
	@Column(name = "email_id", unique = true)
	private String emailId;

	@NotBlank(message = "Password is mandatory")
	@Column(name = "passWord", unique = true)
	private String passWord;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Orders> orders;

	public User() {
		super();
	}
	
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		userId = userId;
	}

	public User(@NotBlank(message = "First Name is mandatory") String firstName,
			@NotBlank(message = "Last Name is mandatory") String lastName,
			@NotBlank(message = "Email Id is mandatory") String emailId,
			@NotBlank(message = "Password is mandatory") String passWord) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.passWord = passWord;
	}



	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return passWord;
	}

	public void setPassword(String password) {
		this.passWord = password;
	}

	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", passWord=" + passWord + ", orders=" + orders + ", getUserId()=" + getUserId() + ", getFirstName()="
				+ getFirstName() + ", getLastName()=" + getLastName() + ", getEmailId()=" + getEmailId()
				+ ", getPassword()=" + getPassword() + ", getOrders()=" + getOrders() + ", getPassWord()="
				+ getPassWord() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
}
