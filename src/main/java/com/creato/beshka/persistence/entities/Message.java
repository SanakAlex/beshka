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

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt == null ? new Timestamp(System.currentTimeMillis()) : createdAt;
        System.out.println(new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Message that = (Message) o;

        if (read != that.read) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        return chat != null ? chat.equals(that.chat) : that.chat == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (read ? 1 : 0);
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        return result;
    }
}
