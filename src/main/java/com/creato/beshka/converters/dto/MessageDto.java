package com.creato.beshka.converters.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long messageId;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NonNull
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @NonNull
    private boolean read = false;
    @NonNull
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
}
