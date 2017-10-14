package com.creato.beshka.converters.dto;

import com.creato.beshka.converters.View;
import com.creato.beshka.persistence.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;

    @JsonView(value = View.Private.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @JsonView(value = View.Private.class)
    private boolean active;
    @JsonView(value = View.Private.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonView(value = View.Private.class)
    private Set<ChatDto> chats = new HashSet<>();

    UserDto(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public UserDto() {
        super();
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (active != userDto.active) return false;
        if (userId != null ? !userId.equals(userDto.userId) : userDto.userId != null) return false;
        if (firstName != null ? !firstName.equals(userDto.firstName) : userDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDto.lastName) : userDto.lastName != null) return false;
        if (email != null ? !email.equals(userDto.email) : userDto.email != null) return false;
        if (password != null ? !password.equals(userDto.password) : userDto.password != null) return false;
        return chats != null ? chats.equals(userDto.chats) : userDto.chats == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
