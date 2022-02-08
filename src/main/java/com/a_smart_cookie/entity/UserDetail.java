package com.a_smart_cookie.entity;

import java.math.BigDecimal;

public final class UserDetail extends Entity<Integer> {
    private static final long serialVersionUID = -4008634113365032333L;
    private final String firstName;
    private final String lastName;
    private final BigDecimal balance;

    public UserDetail(Integer id, String firstName, String lastName, BigDecimal balance) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
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
                "id='" + super.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
