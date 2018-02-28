package com.bridgelabz.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ashwini
 *
 */
@Entity
@Table(name="UserUUID")
public class UserUUID {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	
	@Column(name = "uid")
	private String uid;
	
	@Column(name="email")
	private String email;
	
	/**
	 * @return uuid
	 */
	public String getUid() {
		return uid;
	}
	
	/**
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
