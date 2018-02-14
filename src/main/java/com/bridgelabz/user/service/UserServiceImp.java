package com.bridgelabz.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.repository.RoleRepository;
import com.bridgelabz.user.repository.UserRepository;

@Service
public class UserServiceImp {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	public User saveUser(User user) {
		Role role = roleRepository.findRoleByRoleName(user.getRole().getRoleName());
		user.setRole(role);
		return userRepository.save(user);
	}
	
	public User createUser(User user)
	{
		return userRepository.save(user);
	}
	
	public User getUserByContactNumber(String contactNumber)
	{
		return userRepository.findByContactNumber(contactNumber);
	}
	
	public Role getRoleByRoleName(User user)
	{
		return roleRepository.findRoleByRoleName(user.getRole().getRoleName());
}
	public void saveUserList(List<UserDto> users)
	{
		for(UserDto userdto : users)  {
			User user= new User();
			user.setEmail(userdto.getEmail());
			Role role=roleRepository.findRoleByRoleName(userdto.getRole());
			user.setRole(role);
			userRepository.save(user);
		
	}
	
}
}
