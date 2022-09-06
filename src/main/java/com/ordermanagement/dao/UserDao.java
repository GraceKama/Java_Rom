package com.ordermanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordermanagement.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findByEmailIdAndPassWord(String emailId, String passWord);

}
