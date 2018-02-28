package com.bridgelabz.user.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ashwini
 *
 */
@Entity
@Table(name="Role")
public class Role { 
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "roleName")
	private String roleName;
	
	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return role name
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}