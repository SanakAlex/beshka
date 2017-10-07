package com.creato.beshka.persistence.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

//    @ManyToMany(mappedBy = "members")
//    @JsonIgnore
//    private Set<Chat> chats = new HashSet<>();
}
