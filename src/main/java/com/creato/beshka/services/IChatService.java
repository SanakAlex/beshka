package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.AuthRequiredException;
import com.creato.beshka.exceptions.InputErrorException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.persistence.entities.Chat;

import java.util.List;

public interface IChatService {

    ChatDto getChatById(Long id) throws NoSuchEntityException;

    List<ChatDto> getChatsOfCurrentUser(int offset, int limit) throws NoSuchEntityException, AuthRequiredException;

    ChatDto createChat(ChatDto chatDto) throws InputErrorException;

    ChatDto updateChat(ChatDto chatDto) throws NoSuchEntityException;

    void readMessages(Long id) throws AuthRequiredException, NoSuchEntityException;

    void deleteChat(Long id) throws NoSuchEntityException;

    MessageDto postMessage(MessageDto messageDto) throws InputErrorException, NoSuchEntityException;

    ChatDto getChatDtoWithLastMessage(Chat chat) throws AuthRequiredException;

    ChatDto getChatWithUser(Long id) throws AuthRequiredException, NoSuchEntityException;
}
