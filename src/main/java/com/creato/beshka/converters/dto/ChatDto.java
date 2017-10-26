package com.creato.beshka.converters.dto;

import com.creato.beshka.converters.View;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MessageDto> messages = new LinkedList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<UserDto> members = new HashSet<>();

    @JsonView(View.WithoutMessages.class)
    private MessageDto lastMessage;
    @JsonView(View.WithoutMessages.class)
    private int unread;
}
