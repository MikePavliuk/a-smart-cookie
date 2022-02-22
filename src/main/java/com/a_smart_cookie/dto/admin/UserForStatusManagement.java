package com.a_smart_cookie.dto.admin;

import com.a_smart_cookie.entity.Status;

/**
 * Data transfer object that used as holder of some user information for managing by admin.
 *
 */
public class UserForStatusManagement {
	private final Integer id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final Status status;
	private final int numberOfSubscriptions;

	public UserForStatusManagement(Integer id, String firstName, String lastName, String email, Status status, int numberOfSubscriptions) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
		this.numberOfSubscriptions = numberOfSubscriptions;
	}

	public Integer getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Status getStatus() {
		return status;
	}

	public int getNumberOfSubscriptions() {
		return numberOfSubscriptions;
	}

	@Override
	public String toString() {
		return "UserForStatusManagement{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", status=" + status +
				", numberOfSubscriptions=" + numberOfSubscriptions +
				'}';
	}
}
