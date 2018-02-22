package com.bridgelabz.user.controller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.user.Utility.Validation;
import com.bridgelabz.user.model.Response;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserOTP;
import com.bridgelabz.user.model.UserRegisterDto;
import com.bridgelabz.user.sendMail.SendMail;
import com.bridgelabz.user.service.RedisUserServiceImp;
import com.bridgelabz.user.service.UserServiceImp;

@RestController
public class UserController {

	@Autowired
	UserServiceImp userService;
	Response response = new Response();

	@Autowired
	SendMail sendMail;

	@Autowired
	RedisUserServiceImp redisService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/inviteuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> saveAdmin(@RequestBody List<UserDto> users, HttpServletRequest request,
			String suffix) throws MalformedURLException {
		System.out.println("user..........." + users);
		userService.saveUserList(users);
		userService.sendEmail(users, request);
		response.setMessage("User stored in database successfully......");
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> registerUser(@RequestBody UserRegisterDto userRegisterDto) {

		try {
			userService.registerUser(userRegisterDto);
			response.setMessage("User store in database  successfully......");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/sendotp", method = RequestMethod.POST)
	public ResponseEntity<Response> loginUser(HttpServletRequest request, @RequestHeader String email) {

		boolean isExist = redisService.isMember("EmailPhone", email);

		if (isExist) {
			int otpNumber = 0;
			Random random = new Random();
			otpNumber = random.nextInt(900000) + 100000;
			String to = email;
			String msg = "The One Time Password (OTP) for your login on CoadingCafe is " + otpNumber;
			String subject = "Verfiy Mail";

			sendMail.sendMail(to, subject, msg);
			redisService.save("otpKey", email, String.valueOf(otpNumber));
			response.setMessage("User login successfully......");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);

		}

		return new ResponseEntity<Response>(HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyOTP(@RequestBody UserOTP userOTP) {

		int otp = userOTP.getUserOTP();
		String email = userOTP.getEmail();
		String afterfoundotp = redisService.findOTP(email);

		if (otp == Integer.parseInt(afterfoundotp)) {
			response.setMessage("valid otp ......");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage(" You Enter Wrong OTP  ......");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
}
