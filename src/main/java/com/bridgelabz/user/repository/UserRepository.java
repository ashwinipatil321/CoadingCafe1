package com.bridgelabz.user.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bridgelabz.user.model.Role;
import com.bridgelabz.user.model.User;

/**
 * @author Ashwini Patil
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * find Users By Email
	 * @param email
	 * @return
	 */
	User findUserByEmail(String email);
	
	/**
	 * find user By ContactNumber
	 * @param contactNumber
	 * @return
	 */
	public User findByContactNumber(String contactNumber);
	
	/**
	 * get count of user role
	 * @param role
	 * @return
	 */
	@Query("SELECT count(1) FROM User WHERE role=:role")
	Long getCount(@Param("role") Role role);
	
	@Query("FROM User WHERE role=:role")
	List<User> getContributors(@Param("role") Role role);

}