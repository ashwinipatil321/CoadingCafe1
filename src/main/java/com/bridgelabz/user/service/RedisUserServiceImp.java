package com.bridgelabz.user.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisUserServiceImp implements RedisUserService{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final String KEY = "otpKey";

	@Override
	public int findOTP(String email) {
		
		HashOperations<String, String,Integer> hashOperations = redisTemplate.opsForHash();
		int foundotp = hashOperations.get(KEY, email);
		return foundotp;
	}

	@Override
	public void saveOTP(int otp, String email) {
		
		HashOperations<String, String, Long> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(KEY,email,(long) otp);
	}
}


