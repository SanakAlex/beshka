package com.creato.beshka.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String email;
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
