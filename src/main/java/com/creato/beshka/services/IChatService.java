package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.InputErrorException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.persistence.entities.Chat;

import java.util.List;

public interface IChatService {

    ChatDto getChatById(Long id) throws NoSuchEntityException;

    List<ChatDto> getChatsByUserIn(int offset, int limit) throws NoSuchEntityException;

    ChatDto createChat(ChatDto chatDto) throws InputErrorException;

    ChatDto updateChat(ChatDto chatDto) throws NoSuchEntityException;

    Chat getChatByMember(Long id);

    void deleteChat(Long id) throws NoSuchEntityException;

    MessageDto postMessage(MessageDto messageDto) throws InputErrorException, NoSuchEntityException;
}
