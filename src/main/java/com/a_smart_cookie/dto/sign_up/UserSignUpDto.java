package com.a_smart_cookie.dto.sign_up;

/**
 * Data transfer object that used as holder of values from registration form for user.
 *
 */
public class UserSignUpDto {
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String password;

	public UserSignUpDto(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "UserSingUpDto{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
