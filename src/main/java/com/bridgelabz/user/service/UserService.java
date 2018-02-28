package com.bridgelabz.user.service;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDetailDto;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserRegisterDto;

/**
 * @author Ashwini Patil
 *
 */
public interface UserService {
	
	/**
	 * get User By Email
	 * @param email
	 * @return
	 */
	public User getUserByEmail(String email) ;
	
	/**
	 * @param roleName
	 * @return
	 */
	public long getCount(String roleName) ;
	
	/**
	 * save the user 
	 * @param user
	 * @return
	 */
	public User saveUser(User user);
	
	/**
	 * register user
	 * @param userRegisterDto
	 * @return
	 */
	public User registerUser(UserRegisterDto userRegisterDto);
	
	/**
	 * get User By ContactNumber
	 * @param contactNumber
	 * @return
	 */
	public User getUserByContactNumber(String contactNumber);
	
	/**
	 * save user list
	 * @param users
	 */
	public void saveUserList(List<UserDto> users);
	
	/** 
	 * send email if user is valid for login
	 * @param users
	 * @param request
	 * @throws MalformedURLException
	 */
	public void sendEmail(List<UserDto> users,HttpServletRequest request) throws MalformedURLException;
	
	List<UserDetailDto> getContribuors();
}
