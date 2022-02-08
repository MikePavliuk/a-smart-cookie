package com.a_smart_cookie.entity;

import java.util.List;

public final class User extends Entity<Integer> {
    private static final long serialVersionUID = 1780660780275961115L;
    private final String email;
    private final byte[] password;
    private final byte[] salt;
    private final UserDetail userDetail;
    private final Status status;
    private final Role role;
    private final List<Subscription> subscriptions;

    private User(UserBuilder userBuilder) {
        super.setId(userBuilder.id);
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.salt = userBuilder.salt;
        this.userDetail = userBuilder.userDetail;
        this.status = userBuilder.status;
        this.role = userBuilder.role;
        this.subscriptions = userBuilder.subscriptions;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public Status getStatus() {
        return status;
    }

    public Role getRole() {
        return role;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", email='" + email + '\'' +
                ", userDetail=" + userDetail +
                ", status=" + status.name().toLowerCase() +
                ", role=" + role.name().toLowerCase() +
                ", subscriptions=" + subscriptions +
                '}';
    }

    public static class UserBuilder {
        private Integer id;
        private final String email;
        private final byte[] password;
        private final byte[] salt;
        private UserDetail userDetail;
        private final Status status;
        private final Role role;
        private List<Subscription> subscriptions;

        public User build() {
            return new User(this);
        }

        public UserBuilder(String email,
                           byte[] password,
                           byte[] salt,
                           Status status,
                           Role role) {
            this.email = email;
            this.salt = salt;
            this.password = password;
            this.status = status;
            this.role = role;
        }

        public UserBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
            return this;
        }

        public UserBuilder withSubscriptions(List<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
            return this;
        }

        public UserBuilder fromUser(User user) {
            this.id = user.getId();
            this.userDetail = user.userDetail;
            this.subscriptions = user.subscriptions;
            return this;
        }

    }

    public enum Role {
        SUBSCRIBER,
        ADMIN
    }

    public enum Status {
        ACTIVE,
        BLOCKED
    }

}
