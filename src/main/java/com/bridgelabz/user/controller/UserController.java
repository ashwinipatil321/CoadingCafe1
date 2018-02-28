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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.user.model.Response;
import com.bridgelabz.user.model.UserDetailDto;
import com.bridgelabz.user.model.UserDto;
import com.bridgelabz.user.model.UserOTP;
import com.bridgelabz.user.model.UserRegisterDto;
import com.bridgelabz.user.sendMail.SendMail;
import com.bridgelabz.user.service.RedisUserServiceImp;
import com.bridgelabz.user.service.UserService;

/**
 * @author Ashwini Patil
 *
 */
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	SendMail sendMail;

	@Autowired
	RedisUserServiceImp redisService;

	/**
	 * list of email store in database invited by Admin
	 * 
	 * @param users
	 * @param request
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/inviteuser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> inviteuser(@RequestBody List<UserDto> users, HttpServletRequest request) {

		Response response = new Response();

		try {

			userService.saveUserList(users);
			userService.sendEmail(users, request);
			response.setMessage("User list stored in database successfully......");
			response.setStatus(1);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);

		} catch (MalformedURLException e) {

			response.setMessage("Generate MalformedURLException");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		} catch (Exception e) {

			response.setMessage("User list is not stored in database");
			response.setStatus(-5);
			e.printStackTrace();
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * save users in database
	 * 
	 * @param userRegisterDto
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
		Response response = new Response();

		System.out.println("user deyo in registeration....." + userRegisterDto);

		try {

			userService.registerUser(userRegisterDto);
			response.setMessage("User registered successfully.....");
			response.setStatus(1);
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);

		} catch (Exception e) {

			response.setMessage("User could not be registered....");
			response.setStatus(-1);
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * login user success fully if valid user and send otp to the user email
	 * 
	 * @param request
	 * @param email
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/sendotp", method = RequestMethod.POST)
	public ResponseEntity<Response> loginUser(HttpServletRequest request, @RequestHeader String email) {

		Response response = new Response();
		boolean isExist = redisService.isMember("EmailPhone", email);

		if (isExist == true) {
			try {

				int otpNumber = 0;
				Random random = new Random();
				otpNumber = random.nextInt(900000) + 100000;
				String to = email;
				String msg = "The One Time Password (OTP) for your login on CoadingCafe is " + otpNumber;
				String subject = "Verfiy Mail";

				sendMail.sendMail(to, subject, msg);
				redisService.save("otpKey", email, String.valueOf(otpNumber));
				response.setMessage("User login successfully......");
				response.setStatus(1);
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}

			catch (Exception e) {
				response.setMessage("User could not be registered");
				response.setStatus(-1);
				return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setMessage("User validation Failed");
			response.setStatus(-1);
			return new ResponseEntity<Response>(HttpStatus.OK);
		}

	}

	/**
	 * verfiy user enter otp for login
	 * 
	 * @param userOTP
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyOTP(@RequestBody UserOTP userOTP) {

		Response response = new Response();

		try {

			int otp = userOTP.getUserOTP();
			String email = userOTP.getEmail();
			String afterfoundotp = redisService.findOTP(email);

			if (otp == Integer.parseInt(afterfoundotp)) {
				response.setMessage("valid otp ......");
				response.setStatus(1);
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			} else {
				response.setMessage("invalid otp....");
				response.setStatus(-1);
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {

			response.setMessage(" You Enter Wrong OTP  ......");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * get the total count contributor user entry present in database
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getcontributorcount", method = RequestMethod.GET)
	public long getContributorCount() {

		try {

			return userService.getCount("contributor");

		} catch (Exception e) {

			return 0;
		}
	}

	/**
	 * get the total count approver user entry present in database
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getapprovercount", method = RequestMethod.GET)
	public long getApproverCount() {
		try {
			return userService.getCount("approval");

		} catch (Exception e) {

			return 0;
		}
	}

	/**
	 * get total count viewer user entry present in database
	 * 
	 * @return
	 */
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getviewercount", method = RequestMethod.GET)
	public long getViewerCount() {
		try {

			return userService.getCount("viewer");

		} catch (Exception e) {

			return 0;
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/getcontributors", method = RequestMethod.GET)
	public ResponseEntity<?> getContributors() {
		try {

			List<UserDetailDto> dtos = userService.getContribuors();

			return new ResponseEntity<List<UserDetailDto>>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			Response response = new Response();
			response.setMessage("Something went wrong");
			response.setStatus(-1);
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
