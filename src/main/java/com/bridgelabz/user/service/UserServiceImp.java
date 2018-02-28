package com.bridgelabz.user.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.user.Utility.Validation;
import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDetailDto;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserRegisterDto;
import com.bridgelabz.user.model.UserUUID;
import com.bridgelabz.user.repository.RoleRepository;
import com.bridgelabz.user.repository.UserRepository;
import com.bridgelabz.user.repository.UuidRepository;
import com.bridgelabz.user.sendMail.SendMail;

/**
 * @author Ashwini Patil
 *
 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#getUserByEmail(java.lang.String)
	 */
	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#saveUser(com.bridgelabz.user.model.User)
	 */
	public User saveUser(User user) {
		Role role = roleRepository.findRoleByRoleName(user.getRole().getRoleName());
		user.setRole(role);
		return userRepository.save(user);
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#registerUser(com.bridgelabz.user.model.UserRegisterDto)
	 */
	public User registerUser(UserRegisterDto userRegisterDto) {
		
		Date joinedAt = new Date();
		UserUUID userUUID = uuidRepository.findByUid(userRegisterDto.getUuid());
		User user = userRepository.findUserByEmail(userUUID.getEmail());
		user.setContactNumber(userRegisterDto.getContactNumber());
		user.setName(userRegisterDto.getName());
		user.setJoinedAt(joinedAt);
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#getUserByContactNumber(java.lang.String)
	 */
	public User getUserByContactNumber(String contactNumber) {
		return userRepository.findByContactNumber(contactNumber);
	}

	/**
	 * @param user
	 * @return
	 */
	public Role getRoleByRoleName(User user) {
		return roleRepository.findRoleByRoleName(user.getRole().getRoleName());
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#saveUserList(java.util.List)
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#sendEmail(java.util.List, javax.servlet.http.HttpServletRequest)
	 */
	public void sendEmail(List<UserDto> users, HttpServletRequest request)  {
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
				try {
					url = new URL(request.getRequestURL().toString());
					urlRedirect = url.getProtocol() + "://" + url.getHost() + ":" + 4200 + "/" + request.getContextPath();
					System.out.println("url........." + url);
					System.out.println("urlRedirect........." + urlRedirect);
					msg = msg + " " + urlRedirect + "registeration/" + uniqueId;
					sendMail.sendMail(to, subject, msg);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} 
		}
	

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.UserService#getCount(java.lang.String)
	 */
	public long getCount(String roleName) {

		Role role = roleRepository.findRoleByRoleName(roleName);
		return userRepository.getCount(role);
	}

	@Override
	public List<UserDetailDto> getContribuors() {
		Role role = roleRepository.findRoleByRoleName("contributor");
		List<User> users = userRepository.getContributors(role);
		
		List<UserDetailDto> dtos = new ArrayList<>();
		
		for (User user : users) {
			UserDetailDto dto = new UserDetailDto(user);
			dtos.add(dto);
		}
		
		return dtos;
	}
	
}
