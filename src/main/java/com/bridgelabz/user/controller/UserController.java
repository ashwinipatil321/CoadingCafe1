package com.bridgelabz.user.controller;
import java.util.List;
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
import com.bridgelabz.user.sendMail.SendMail;
import com.bridgelabz.user.service.UserServiceImp;

@RestController
public class UserController {

	@Autowired
	UserServiceImp userService;
	Response response = new Response();

	@Autowired
	SendMail sendMail;

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
				sendMail.sendMail(to, subject, msg, url);
		}
		response.setMessage("User stored in database successfully......");
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> registerUser(@RequestBody User user) {

		User userEmail=userService.getUserByEmail(user.getEmail());
		User contactNumber1 =	userService.getUserByContactNumber(user.getContactNumber());
		if(userEmail==null && contactNumber1==null)
		{
			User userDetail = userService.createUser(user);

			if (userDetail != null) {

				response.setMessage("User Registered  successfully......");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage("Conflict to Stored User in database........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMessage("User is not valid..........");
		return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
	}
}
