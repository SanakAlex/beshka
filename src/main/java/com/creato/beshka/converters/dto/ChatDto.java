package com.creato.beshka.converters.dto;

import com.creato.beshka.converters.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long chatId;
    private String chatTitle;

    @JsonView(View.WithMessages.class)
    private List<MessageDto> messages = new LinkedList<>();

    @JsonView(View.WithoutMessages.class)
    private MessageDto lastMessage;
    @JsonView(View.WithoutMessages.class)
    private int unread;

    public ChatDto(long chatId, String chatTitle, long messageId, String content, Date createdAt, boolean read){
        this.chatId = chatId;
        this.chatTitle = chatTitle;
        this.lastMessage = new MessageDto(messageId, content, Timestamp.valueOf(createdAt.toString()), read);
        this.unread = 0;
    }
}
