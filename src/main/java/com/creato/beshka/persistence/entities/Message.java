package com.creato.beshka.persistence.entities;

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
    private Timestamp createdAt;
    private boolean read;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="senderId")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chatId")
    private Chat chat;
}
