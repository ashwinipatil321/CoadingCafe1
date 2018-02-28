package com.bridgelabz.user.redisConfigration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Ashwini Patil
 *
 */
@Configuration
public class RedisConfigration {
	
	/**
	 * jedis connectivity confguration of user
	 * @return
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
	    jedisConnectionFactory.setHostName("localhost");
	    jedisConnectionFactory.setPort(6379);
	    return jedisConnectionFactory;
	}
	
	/**
	 * redisTemplate setting connectivity with redis
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    return template;
	}

}
