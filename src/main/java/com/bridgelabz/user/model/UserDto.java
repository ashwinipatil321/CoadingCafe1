package com.bridgelabz.user.model;

/**
 * @author Ashwini Patil
 *
 */
public class UserDto {

	private String email;
	private String role;

	
	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @param role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}
