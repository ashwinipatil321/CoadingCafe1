package com.bridgelabz.user.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.user.Utility.Validation;
import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserRegisterDto;
import com.bridgelabz.user.model.UserUUID;
import com.bridgelabz.user.repository.RoleRepository;
import com.bridgelabz.user.repository.UserRepository;
import com.bridgelabz.user.repository.UuidRepository;
import com.bridgelabz.user.sendMail.SendMail;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UuidRepository uuidRepository;

	@Autowired
	RedisUserService redisService;

	@Autowired
	SendMail sendMail;

	@Autowired
	Validation validation;

	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	public User saveUser(User user) {
		Role role = roleRepository.findRoleByRoleName(user.getRole().getRoleName());
		user.setRole(role);
		return userRepository.save(user);
	}

	public User registerUser(UserRegisterDto userRegisterDto) {
		UserUUID userUUID = uuidRepository.findByUid(userRegisterDto.getUuid());
		User user = userRepository.findUserByEmail(userUUID.getEmail());
		user.setContactNumber(userRegisterDto.getContactNumber());
		user.setName(userRegisterDto.getName());
		userRepository.save(user);
		uuidRepository.delete(userUUID);
		String email = user.getEmail();
		String contactNumber = user.getContactNumber();
		boolean validContactNumber = Validation.mobileValidation(contactNumber);
		boolean validEmail =  Validation.emailValidation(email);
		if(validContactNumber== true && validEmail==true)
		{
		redisService.save("EmailPhone", email);
		redisService.save("EmailPhone", contactNumber);
		}
		return user;
	}

	public User getUserByContactNumber(String contactNumber) {
		return userRepository.findByContactNumber(contactNumber);
	}

	public Role getRoleByRoleName(User user) {
		return roleRepository.findRoleByRoleName(user.getRole().getRoleName());
	}

	public void saveUserList(List<UserDto> users) {

		for (UserDto userdto : users) {

			User user = new User();
			Role role = roleRepository.findRoleByRoleName(userdto.getRole());
			String email = userdto.getEmail();
			boolean emailValid = Validation.emailValidation(email);
			if (emailValid==true && email!=null) {
				user.setEmail(email);
				user.setRole(role);
				userRepository.save(user);
			} else {
				System.out.println("Email is not valid......");
			}
		}
	}

	public void sendEmail(List<UserDto> users, HttpServletRequest request) throws MalformedURLException {
		String urlRedirect;
		URL url;
		UserUUID userUUID = new UserUUID();

		for (UserDto userdto : users) {
				String email = userdto.getEmail();
				String to = email;
				String msg = "click link to verfiy your account ";
				String subject = "Verfiy Mail";
				String uniqueId = UUID.randomUUID().toString();
				userUUID.setEmail(to);
				userUUID.setUid(uniqueId);
				uuidRepository.save(userUUID);
				url = new URL(request.getRequestURL().toString());
				urlRedirect = url.getProtocol() + "://" + url.getHost() + ":" + 4200 + "/" + request.getContextPath();
				System.out.println("url........." + url);
				System.out.println("urlRedirect........." + urlRedirect);
				msg = msg + " " + urlRedirect + "registeration/" + uniqueId;
				sendMail.sendMail(to, subject, msg);
			} 
		}
	

	public long getCount(String roleName) {

		Role role = roleRepository.findRoleByRoleName(roleName);
		return userRepository.getCount(role);
	}
	
}
