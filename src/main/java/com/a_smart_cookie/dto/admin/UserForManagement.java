package com.a_smart_cookie.dto.admin;

import com.a_smart_cookie.entity.Status;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Data transfer object that used as holder of some user information for managing by admin.
 *
 */
public class UserForManagement {
	private final Integer id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final Status status;
	private final int numberOfActiveSubscriptions;
	private final int numberOfInactiveSubscriptions;
	private final BigDecimal totalSpentMoney;

	public UserForManagement(Integer id,
							 String firstName,
							 String lastName,
							 String email,
							 Status status,
							 int numberOfActiveSubscriptions,
							 int numberOfInactiveSubscriptions,
							 BigDecimal totalSpentMoney) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.status = status;
		this.numberOfActiveSubscriptions = numberOfActiveSubscriptions;
		this.numberOfInactiveSubscriptions = numberOfInactiveSubscriptions;
		this.totalSpentMoney = Objects.requireNonNullElse(totalSpentMoney, BigDecimal.ZERO);
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

	public int getNumberOfActiveSubscriptions() {
		return numberOfActiveSubscriptions;
	}

	public int getNumberOfInactiveSubscriptions() {
		return numberOfInactiveSubscriptions;
	}

	public BigDecimal getTotalSpentMoney() {
		return totalSpentMoney;
	}

	@Override
	public String toString() {
		return "UserForStatusManagement{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", status=" + status +
				", numberOfActiveSubscriptions=" + numberOfActiveSubscriptions +
				", numberOfInactiveSubscriptions=" + numberOfInactiveSubscriptions +
				", totalSpentMoney=" + totalSpentMoney +
				'}';
	}

}
