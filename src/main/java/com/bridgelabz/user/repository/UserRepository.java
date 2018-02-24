package com.bridgelabz.user.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findUserByEmail(String email);
	
	public User findByContactNumber(String contactNumber);
	
	@Query("SELECT count(1) FROM User WHERE role=:role")
	Long getCount(@Param("role") Role role);
	
	@Query("SELECT count(1) FROM User")
	Long getTotalCount( Role role);
}