package com.creato.beshka.converters.dto;

import com.creato.beshka.converters.View;
import com.creato.beshka.persistence.entities.Message;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long chatId;
    private String chatTitle;

    @JsonView(View.WithMessages.class)
    private List<MessageDto> messages = new LinkedList<>();
    @JsonView(View.WithMessages.class)
    private Set<UserDto> members = new HashSet<>();

    @JsonView(View.WithoutMessages.class)
    private MessageDto lastMessage;
    @JsonView(View.WithoutMessages.class)
    private int unread;

    public ChatDto(long chatId, String chatTitle, Message message){
        this.chatId = chatId;
        this.chatTitle = chatTitle;
        this.lastMessage = new MessageDto
                (message.getMessageId(), message.getContent(), message.getCreatedAt(), message.isRead(),
                        new UserDto(message.getSender()));
        this.unread = 0;
    }
}
