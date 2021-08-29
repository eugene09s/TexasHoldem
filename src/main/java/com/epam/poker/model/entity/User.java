package com.epam.poker.model.entity;

import com.epam.poker.model.Entity;
import com.epam.poker.model.enumeration.UserRole;
import com.epam.poker.model.enumeration.UserStatus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringJoiner;

public class User implements Entity {
    private BigInteger userId;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal balance;
    private String photo;
    private String createTime;
    private long phoneNumber;
    private String aboutYourselt;
    private UserRole userRole;
    private UserStatus userStatus;

    public User(BigInteger userId, String login, String password, String firstName, String lastName,
                String email, BigDecimal balance, String photo, String createTime, long phoneNumber,
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

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

        if (getPhoneNumber() != user.getPhoneNumber()) return false;
        if (getUserId() != null ? !getUserId().equals(user.getUserId()) : user.getUserId() != null) return false;
        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getBalance() != null ? !getBalance().equals(user.getBalance()) : user.getBalance() != null) return false;
        if (getPhoto() != null ? !getPhoto().equals(user.getPhoto()) : user.getPhoto() != null) return false;
        if (getCreateTime() != null ? !getCreateTime().equals(user.getCreateTime()) : user.getCreateTime() != null)
            return false;
        if (getAboutYourselt() != null ? !getAboutYourselt().equals(user.getAboutYourselt()) : user.getAboutYourselt() != null)
            return false;
        if (getUserRole() != user.getUserRole()) return false;
        return getUserStatus() == user.getUserStatus();
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        result = 31 * result + (getPhoto() != null ? getPhoto().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (int) (getPhoneNumber() ^ (getPhoneNumber() >>> 32));
        result = 31 * result + (getAboutYourselt() != null ? getAboutYourselt().hashCode() : 0);
        result = 31 * result + (getUserRole() != null ? getUserRole().hashCode() : 0);
        result = 31 * result + (getUserStatus() != null ? getUserStatus().hashCode() : 0);
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
                .add("photo='" + photo + "'")
                .add("createTime='" + createTime + "'")
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

        public UserBuilder setUserId(BigInteger userId) {
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

        public UserBuilder setPhoto(String photo) {
            user.setPhoto(photo);
            return this;
        }

        public UserBuilder setCreateTime(String createTime) {
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