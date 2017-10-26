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
    private String avatarUrlThumbnail;
    private String avatarUrlLarge;

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

    public UserDto() {
        super();
        this.active = true;
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.avatarUrlLarge = user.getAvatarUrlLarge();
        this.avatarUrlThumbnail = user.getAvatarUrlThumbnail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto user = (UserDto) o;

        if (active != user.active) return false;
        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (avatarUrlThumbnail != null ? !avatarUrlThumbnail.equals(user.avatarUrlThumbnail) : user.avatarUrlThumbnail != null)
            return false;
        if (avatarUrlLarge != null ? !avatarUrlLarge.equals(user.avatarUrlLarge) : user.avatarUrlLarge != null)
            return false;
        return chats != null ? chats.equals(user.chats) : user.chats == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (avatarUrlThumbnail != null ? avatarUrlThumbnail.hashCode() : 0);
        result = 31 * result + (avatarUrlLarge != null ? avatarUrlLarge.hashCode() : 0);
        return result;
    }
}
