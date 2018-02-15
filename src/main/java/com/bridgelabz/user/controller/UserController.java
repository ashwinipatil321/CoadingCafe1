package com.bridgelabz.user.controller;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.user.model.Response;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserOTP;
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
	@RequestMapping(value = "/saveAdmin", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE, produces =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> saveAdmin(@RequestBody List<UserDto> users,HttpServletRequest request)
	{
		userService.saveUserList(users);

		for(UserDto userdto : users)
		{
			String to = userdto.getEmail();
			String msg = "click link to verfiy your account ";
			String subject = "Verfiy Mail";

			String url = request.getRequestURI().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "localhost:4200/registeration";
			msg = msg+" "+"http://"+url;
			sendMail.sendMail(to, subject, msg);
		}
		response.setMessage("User stored in database successfully......");
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> registerUser(@RequestBody User user) {

		User userEmail=userService.getUserByEmail(user.getEmail());
		User contactNumber1 =	userService.getUserByContactNumber(user.getContactNumber());
		if(userEmail==null && contactNumber1==null)
		{
			User userDetail = userService.createUser(user);

			if (userDetail != null) {

				response.setMessage("User store in database  successfully......");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage(" Not Stored User in database........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMessage("User is already present in database .........");
		return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/sendOTP", method = RequestMethod.POST)
	public ResponseEntity<Response> loginUser(@RequestBody User user,HttpServletRequest request) {

		int otpNumber = 0;
		Random random = new Random();
		otpNumber = random.nextInt(900000) + 100000;
		String email=user.getEmail();
		String to = email;
		String msg = "The One Time Password (OTP) for your login on CoadingCafe is " + otpNumber;
		String subject = "Verfiy Mail";

		User userEmail = userService.getUserByEmail(email);
		if(userEmail.getEmail().equals(email))
		{
			sendMail.sendMail(to, subject, msg);
			redisService.saveOTP(otpNumber,email);
			response.setMessage("User login successfully......");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage("User not present in database ......");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/verifyOTP", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyOTP(@RequestBody UserOTP userOTP ) {

		int otp = userOTP.getUserOTP();
		String email=userOTP.getEmail();
		System.out.println("userOtp.....>"+otp);
		System.out.println("email.....>"+email);
		int afterfoundotp = redisService.findOTP(email);

		if(otp==afterfoundotp)
		{
			response.setMessage("valid otp ......");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage(" You Enter Wrong OTP  ......");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
}
