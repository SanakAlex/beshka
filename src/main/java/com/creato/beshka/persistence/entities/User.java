package com.creato.beshka.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "\"user\"")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private boolean active;
    private String firstName;
    private String lastName;
    private String password;

//    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
//    private Set<Chat> chats = new HashSet<>();

    public void setPassword(String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
