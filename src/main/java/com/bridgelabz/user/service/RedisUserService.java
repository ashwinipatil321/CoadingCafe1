package com.bridgelabz.user.service;

public interface RedisUserService {
	void saveOTP(int otp,String email);
	int findOTP(String email);
}
