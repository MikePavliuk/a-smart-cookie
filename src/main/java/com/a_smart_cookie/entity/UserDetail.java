package com.a_smart_cookie.entity;

import java.math.BigDecimal;

/**
 * UserDetail entity.
 *
 */
public final class UserDetail extends Entity {
    private static final long serialVersionUID = -4008634113365032333L;
	private Integer id;
    private final String firstName;
    private final String lastName;
    private final BigDecimal balance;

    public UserDetail(Integer id, String firstName, String lastName, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

	public UserDetail(String firstName, String lastName, BigDecimal balance) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

	@Override
	public String toString() {
		return "UserDetail{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", balance=" + balance +
				'}';
	}
}
