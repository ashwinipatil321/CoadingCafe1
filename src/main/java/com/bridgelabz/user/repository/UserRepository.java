package com.bridgelabz.user.repository;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.user.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findUserByEmail(String email);
//	void  save(String email);
//	User save(User user);
	public User findByContactNumber(String contactNumber);
}