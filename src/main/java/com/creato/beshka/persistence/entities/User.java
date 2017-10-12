package com.creato.beshka.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String email;
    @NonNull
    private boolean active = true;
    private String firstName;
    private String lastName;
    private String password;

    public User(Long userId) {
        this.userId = userId;
    }

//    @ManyToMany(mappedBy = "members")
//    @JsonIgnore
//    private Set<Chat> chats = new HashSet<>();
}
