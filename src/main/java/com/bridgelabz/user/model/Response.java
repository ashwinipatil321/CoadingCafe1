package com.bridgelabz.user.model;

/**
 * @author Ashwini
 *
 */
public class Response {

	private String message;
	private int status;

	/**
	 * @return response  status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return response message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
