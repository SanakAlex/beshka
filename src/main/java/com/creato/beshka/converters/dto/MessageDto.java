package com.creato.beshka.converters.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long messageId;
    private String content;
//    TODO solve timezone problem
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT+3")
    private Timestamp createdAt;
    private boolean read;
    private UserDto sender;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ChatDto chat;

    MessageDto(Long messageId, String content, Timestamp createdAt, boolean read, UserDto sender) {
        this.messageId = messageId;
        this.content = content;
        this.createdAt = createdAt;
        this.read = read;
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MessageDto that = (MessageDto) o;

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
