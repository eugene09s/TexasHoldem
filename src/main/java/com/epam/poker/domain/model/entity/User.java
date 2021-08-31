package com.epam.poker.domain.model.entity;

import com.epam.poker.domain.model.Entity;
import com.epam.poker.domain.model.enumeration.UserRole;
import com.epam.poker.domain.model.enumeration.UserStatus;

import java.math.BigDecimal;
import java.sql.Blob;
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
    private Blob photo;
    private Timestamp createTime;
    private long phoneNumber;
    private String aboutYourselt;
    private UserRole userRole;
    private UserStatus userStatus;

    public User(long userId, String login, String password, String firstName, String lastName,
                String email, BigDecimal balance, Blob photo, Timestamp createTime, long phoneNumber,
                String aboutYourselt, UserRole userRole, UserStatus userStatus) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.balance = balance;
        this.photo = photo;
        this.createTime = createTime;
        this.phoneNumber = phoneNumber;
        this.aboutYourselt = aboutYourselt;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAboutYourselt() {
        return aboutYourselt;
    }

    public void setAboutYourselt(String aboutYourselt) {
        this.aboutYourselt = aboutYourselt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
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
        if (photo != null ? !photo.equals(user.photo) : user.photo != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null) return false;
        if (aboutYourselt != null ? !aboutYourselt.equals(user.aboutYourselt) : user.aboutYourselt != null)
            return false;
        if (userRole != user.userRole) return false;
        return userStatus == user.userStatus;
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
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
        result = 31 * result + (aboutYourselt != null ? aboutYourselt.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
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
                .add("photo=" + photo)
                .add("createTime=" + createTime)
                .add("phoneNumber=" + phoneNumber)
                .add("aboutYourselt='" + aboutYourselt + "'")
                .add("userRole=" + userRole)
                .add("userStatus=" + userStatus)
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

        public UserBuilder setPhoto(Blob photo) {
            user.setPhoto(photo);
            return this;
        }

        public UserBuilder setCreateTime(Timestamp createTime) {
            user.setCreateTime(createTime);
            return this;
        }

        public UserBuilder setPhoneNumber(long phoneNumber) {
            user.setPhoneNumber(phoneNumber);
            return this;
        }

        public UserBuilder setAboutYourselt(String aboutYourselt) {
            user.setAboutYourselt(aboutYourselt);
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

        public User createUser() {
            return new User();
        }
    }
}