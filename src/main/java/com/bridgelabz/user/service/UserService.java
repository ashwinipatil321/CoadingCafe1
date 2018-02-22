package com.bridgelabz.user.service;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserRegisterDto;

public interface UserService {
	public User getUserByEmail(String email) ;

	public User saveUser(User user);
	public User registerUser(UserRegisterDto userRegisterDto);
	public User getUserByContactNumber(String contactNumber);
	public void saveUserList(List<UserDto> users);
	public void sendEmail(List<UserDto> users,HttpServletRequest request) throws MalformedURLException;
}
