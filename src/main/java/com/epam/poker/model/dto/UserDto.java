package com.epam.poker.model.dto;

import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserDto {
    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private UserStatus userStatus;
    private Timestamp createTime;
    private String photo;
    private BigDecimal balance;

    public UserDto(long id, String firstName, String lastName, UserRole userRole,
                   UserStatus userStatus, Timestamp createTime, String photo,
                   BigDecimal balance, String login) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.createTime = createTime;
        this.photo = photo;
        this.balance = balance;
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private long id;
        private String firstName;
        private String lastName;
        private UserRole userRole;
        private UserStatus userStatus;
        private Timestamp createTime;
        private String photo;
        private BigDecimal balance;
        private String login;

        public UserDtoBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDtoBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDtoBuilder setUserRole(UserRole userRole) {
            this.userRole = userRole;
            return this;
        }

        public UserDtoBuilder setUserStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public UserDtoBuilder setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
            return this;
        }

        public UserDtoBuilder setPhoto(String photo) {
            this.photo = photo;
            return this;
        }

        public UserDtoBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public UserDtoBuilder setLogin(String login) {
            this.login = login;
            return this;
        }

        public UserDto createUserDto() {
            return new UserDto(id, firstName, lastName, userRole, userStatus,
                    createTime, photo, balance, login);
        }
    }
}
