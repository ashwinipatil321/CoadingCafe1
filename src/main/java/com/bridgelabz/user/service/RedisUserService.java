package com.bridgelabz.user.service;

/**
 * @author Ashwini Patil
 *
 */
public interface RedisUserService {
	/**
	 * save user in redis
	 * @param key
	 * @param hkey
	 * @param hvalue
	 */
	void save(String key, String hkey, String hvalue);
	
	/**
	 * find OTP by email
	 * @param email
	 * @return
	 */
	String findOTP(String email);
	
	/**
	 * save user
	 * @param key
	 * @param value
	 */
	void save(String key, String value);
}
