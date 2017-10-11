package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.persistence.dao.ChatRepository;
import com.creato.beshka.persistence.dao.MessageRepository;
import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements IChatService {

    private final ModelMapper modelMapper;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatServiceImpl(ModelMapper modelMapper, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.modelMapper = modelMapper;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public ChatDto getChatById(Long id) throws NoSuchEntityException {
        Chat chat = chatRepository.findOne(id);
        if (chat == null)
            throw new NoSuchEntityException("chat", "id: " + id);
        return modelMapper.map(chat, ChatDto.class);
    }

    @Override
    public List<ChatDto> getChatsByUserIn(PageRequest pageRequest) throws NoSuchEntityException {
//        TODO add security auth of user ZATICHKAAAAAA
//        User user = new User();
//        return chatRepository.findAllByMembersContains(user);
        Page<Chat> chats = chatRepository.findAll(pageRequest);
        if (chats == null || chats.getContent().isEmpty())
            throw new NoSuchEntityException(Chat.class.getName(), String.format("[offset: %d, limit: %d]", pageRequest.getOffset(), pageRequest.getPageSize()));
        List<ChatDto> chatDtos = chatRepository.getChatDtosWithLastMessage();
        return chatDtos.stream()
                .peek(chat -> chat.setUnread(messageRepository.getUnreadCount(chat.getChatId())))
                .collect(Collectors.toList());
    }

    @Override
    public ChatDto createChat(ChatDto chatDto) {
        Chat chat = modelMapper.map(chatDto, Chat.class);
        Chat createdChat = chatRepository.save(chat);
        return modelMapper.map(createdChat, ChatDto.class);
    }

    @Override
    public ChatDto updateChat(ChatDto chatDto) throws NoSuchEntityException {
//        TODO check if a member of chat or admin
        if (chatRepository.findOne(chatDto.getChatId()) == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId: " + chatDto.getChatId());
        Chat chat = modelMapper.map(chatDto, Chat.class);
        Chat updatedChat = chatRepository.save(chat);
        return modelMapper.map(updatedChat, ChatDto.class);
    }

    @Override
    public void deleteChat(Long id)  throws NoSuchEntityException{
        if (chatRepository.findOne(id) == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId: " + id);
        chatRepository.delete(id);
    }

    @Override
    public MessageDto postMessage(MessageDto messageDto) {
        Message message = modelMapper.map(messageDto, Message.class);
        Message createdMessage = messageRepository.save(message);
        return modelMapper.map(createdMessage, MessageDto.class);
    }

    @Override
    public Chat getChatByMember(Long id) {
        return null;
//        return chatsOfCurrentUser.stream().
//                filter(chat-> chat.getMembers().stream().
//                        anyMatch(user->user.getUserId().equals(id))).findFirst().get();
    }

    @Override
    public List<Message> getChatsMessages(Long id) {
//        return messageRepository.findAllByChat_ChatId(id);
        return null;
    }

}
