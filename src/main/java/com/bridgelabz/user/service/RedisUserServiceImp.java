package com.bridgelabz.user.service;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;



/**
 * @author Ashwini Patil
 *
 */
@Service
public class RedisUserServiceImp implements RedisUserService{

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final String KEY = "otpKey";

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.RedisUserService#findOTP(java.lang.String)
	 */
	@Override
	public String findOTP(String email) {
		
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		
		String foundotp = hashOperations.get(KEY, email);
		return foundotp;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.RedisUserService#save(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void save(String key, String hkey, String hvalue) {
		
		HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(key ,hkey,hvalue);
	}
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.user.service.RedisUserService#save(java.lang.String, java.lang.String)
	 */
	@Override
	public void save(String key, String value) {
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		Long returnValue = setOperations.add(key, value);
		System.out.println(returnValue+" "+key);
	}
	
	/**
	 * chech user exits in redis or not
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean isMember(String key, String value) {
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		return setOperations.isMember(key, value);
	}
	
}


