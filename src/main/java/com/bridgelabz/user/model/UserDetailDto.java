package com.bridgelabz.user.model;

import java.util.Date;

/**
 * @author bridgeit
 *
 */
public class UserDetailDto {

	String name;
	String contactNumber;
	Date joinedAt;

	public UserDetailDto() {

	}

	public UserDetailDto(User user) {
		this.name = user.getName();
		this.contactNumber = user.getContactNumber();
		this.joinedAt = user.getJoinedAt();
	}

	public String getName() {
		return name;
	}

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	
}
