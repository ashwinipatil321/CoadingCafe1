package com.bridgelabz.user.Utility;

import org.springframework.stereotype.Component;

/**
 * @author Ashwini Patil
 *
 */
@Component
public class Validation {

	private  static final  String emailValidation="^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
	private  static final String mobileValidation="^((\\+)?(\\d{2}[-]))?(\\d{10}){1}?$";

	/**
	 * enail validation
	 * @param email
	 * @return
	 */
	public  static boolean emailValidation(String email) {

		boolean isMatchEmail = false;

		if(email.matches(emailValidation)) {

			isMatchEmail = true;
			return isMatchEmail;

		}
		return isMatchEmail; 
	}

	/**
	 * phone number validation
	 * @param phoneNumber
	 * @return
	 */
	public static boolean mobileValidation(String phoneNumber)
	{
		boolean isMatchPhobeNumber = false;

		if(phoneNumber.matches(mobileValidation)) {

			isMatchPhobeNumber = true;
			return isMatchPhobeNumber;

		}
		return isMatchPhobeNumber; 
	}
}
