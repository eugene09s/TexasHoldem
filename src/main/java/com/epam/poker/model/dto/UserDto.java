package com.epam.poker.model.dto;

import com.epam.poker.model.database.type.UserRole;
import com.epam.poker.model.database.type.UserStatus;

import java.sql.Timestamp;

public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private UserStatus userStatus;
    private Timestamp createTime;
    private String photo;

    public UserDto(long id, String firstName, String lastName, UserRole userRole,
                   UserStatus userStatus, Timestamp createTime, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.createTime = createTime;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != userDto.id) return false;
        if (firstName != null ? !firstName.equals(userDto.firstName) : userDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
        if (userRole != userDto.userRole) return false;
        if (userStatus != userDto.userStatus) return false;
        if (createTime != null ? !createTime.equals(userDto.createTime) : userDto.createTime != null) return false;
        return photo != null ? photo.equals(userDto.photo) : userDto.photo == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", photo='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
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

        public UserDto createUserDto() {
            return new UserDto(id, firstName, lastName, userRole, userStatus, createTime, photo);
        }
    }
}
