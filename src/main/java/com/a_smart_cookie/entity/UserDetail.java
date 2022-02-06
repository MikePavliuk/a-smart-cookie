package com.a_smart_cookie.entity;

import java.math.BigDecimal;

public final class UserDetail {
    private final String firstName;
    private final String lastName;
    private final BigDecimal balance;

    public UserDetail(String firstName, String lastName, BigDecimal balance) {
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
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
