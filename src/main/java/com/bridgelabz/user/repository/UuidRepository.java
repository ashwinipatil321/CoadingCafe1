package com.bridgelabz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.user.model.UserUUID;

/**
 * @author Ashwini Patil
 *
 */
public interface UuidRepository extends JpaRepository<UserUUID, Integer> {

	/**
	 * find uuid
	 * @param uid
	 * @return
	 */
	UserUUID findByUid(String uid);
}
