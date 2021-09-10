package com.epam.poker.model.entity;

import com.epam.poker.model.entity.type.UserRole;
import com.epam.poker.model.entity.type.UserStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.StringJoiner;

public class User implements Entity {
    private long userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
    private UserRole userRole;
    private UserStatus userStatus;
    private long phoneNumber;
    private Timestamp createTime;

    public User(long userId, String login, String password, String firstName, String lastName,
                String email, BigDecimal balance, UserRole userRole, UserStatus userStatus,
                long phoneNumber, Timestamp createTime) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.phoneNumber = phoneNumber;
        this.createTime = createTime;
    }

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public User setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public User setUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public User setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (phoneNumber != user.phoneNumber) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (balance != null ? !balance.equals(user.balance) : user.balance != null) return false;
        if (userRole != user.userRole) return false;
        if (userStatus != user.userStatus) return false;
        return createTime != null ? createTime.equals(user.createTime) : user.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("email='" + email + "'")
                .add("balance=" + balance)
                .add("userRole=" + userRole)
                .add("userStatus=" + userStatus)
                .add("phoneNumber=" + phoneNumber)
                .add("createTime=" + createTime)
                .toString();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder setUserId(long userId) {
            user.setUserId(userId);
            return this;
        }

        public UserBuilder setLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public UserBuilder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public UserBuilder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public UserBuilder setBalance(BigDecimal balance) {
            user.setBalance(balance);
            return this;
        }

        public UserBuilder setUserRole(UserRole userRole) {
            user.setUserRole(userRole);
            return this;
        }

        public UserBuilder setUserStatus(UserStatus userStatus) {
            user.setUserStatus(userStatus);
            return this;
        }

        public UserBuilder setPhoneNumber(long phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }

        public UserBuilder setCreateTime(Timestamp timestamp) {
            user.setCreateTime(timestamp);
            return this;
        }

        public User createUser() {
            return user;
        }
    }
}