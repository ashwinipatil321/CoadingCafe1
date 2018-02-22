package com.bridgelabz.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.user.model.UserUUID;

public interface UuidRepository extends JpaRepository<UserUUID, Integer> {

	UserUUID findByUid(String uid);
}
