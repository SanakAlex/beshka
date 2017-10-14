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
}
