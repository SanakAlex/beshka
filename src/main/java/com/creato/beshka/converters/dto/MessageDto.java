package com.creato.beshka.converters.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class MessageDto {
    private Long messageId;
    private String content;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private boolean read = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ChatDto chat;

    public MessageDto(Long messageId, String content, Timestamp createdAt, boolean read) {
        this.messageId = messageId;
        this.content = content;
        this.createdAt = createdAt;
        this.read = read;
    }
}
