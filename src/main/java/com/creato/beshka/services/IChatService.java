package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.Message;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IChatService {

    ChatDto getChatById(Long id) throws NoSuchEntityException;

    List<ChatDto> getChatsByUserIn(PageRequest pageRequest) throws NoSuchEntityException;

    ChatDto createChat(ChatDto chatDto);

    ChatDto updateChat(ChatDto chatDto) throws NoSuchEntityException;

    Chat getChatByMember(Long id);

    List<Message> getChatsMessages(Long id);

    void deleteChat(Long id) throws NoSuchEntityException;

    MessageDto postMessage(MessageDto messageDto);
}
