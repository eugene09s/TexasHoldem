package com.epam.poker.model.dto;

import com.epam.poker.model.Entity;
import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserDto implements Entity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != userDto.id) return false;
        if (login != null ? !login.equals(userDto.login) : userDto.login != null) return false;
        if (firstName != null ? !firstName.equals(userDto.firstName) : userDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
        if (userRole != userDto.userRole) return false;
        if (userStatus != userDto.userStatus) return false;
        if (createTime != null ? !createTime.equals(userDto.createTime) : userDto.createTime != null) return false;
        if (photo != null ? !photo.equals(userDto.photo) : userDto.photo != null) return false;
        return balance != null ? balance.equals(userDto.balance) : userDto.balance == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
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
