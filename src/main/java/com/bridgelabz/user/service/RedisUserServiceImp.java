package com.bridgelabz.user.service;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;



@Service
public class RedisUserServiceImp implements RedisUserService{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final String KEY = "otpKey";

	@Override
	public String findOTP(String email) {
		
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		
		String foundotp = hashOperations.get(KEY, email);
		return foundotp;
	}

	@Override
	public void save(String key, String hkey, String hvalue) {
		
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(key ,hkey,hvalue);
	}
	
	@Override
	public void save(String key, String value) {
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		Long returnValue = setOperations.add(key, value);
		System.out.println(returnValue+" "+key);
	}
	public void getData(String key)
	{
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		Set<Object> member = setOperations.members(key);
		System.out.println(member.toString());
	}
	
	public boolean isMember(String key, String value) {
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		return setOperations.isMember(key, value);
	}
	
}


