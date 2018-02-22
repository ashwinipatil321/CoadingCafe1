package com.bridgelabz.user.service;

public interface RedisUserService {
	void save(String key, String hkey, String hvalue);
	String findOTP(String email);
	void save(String key, String value);
}
