package com.creato.beshka.converters.dto;

import com.creato.beshka.converters.View;
import com.creato.beshka.persistence.entities.User;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String firstName;

    private String lastName;
    @JsonView(value = View.Private.class)
    private String email;
    @JsonView(value = View.Private.class)
    @NonNull
    private boolean active = true;
    @JsonView(value = View.Private.class)
    private String password;

    UserDto(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
