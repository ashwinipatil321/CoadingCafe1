package com.bridgelabz.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	
	public String getUid() {
		return uid;
	}
	public String getEmail() {
		return email;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
