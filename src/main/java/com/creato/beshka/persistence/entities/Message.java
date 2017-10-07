package com.creato.beshka.persistence.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long messageId;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="senderId")
//    private User sender;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="chatId")
//    @JsonIgnore
//    private Chat chat;
}
