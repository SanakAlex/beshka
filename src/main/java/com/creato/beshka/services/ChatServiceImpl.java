package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.InputErrorException;
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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ChatDto getChatById(Long id) throws NoSuchEntityException {
        Chat chat = chatRepository.findOne(id);
        if (chat == null)
            throw new NoSuchEntityException(Chat.class.getName(), "id: " + id);
        return modelMapper.map(chat, ChatDto.class);
    }

    /**
     * offset number of rows to skip
     * limit max on request
     */
    @Override
    @Transactional
    public List<ChatDto> getChatsByUserIn(int offset, int limit) throws NoSuchEntityException {
//        TODO add security auth of user ZATICHKAAAAAA
//        User user = new User();
//        return chatRepository.findAllByMembersContains(user);
        Page<ChatDto> chatDtos = chatRepository.getChatDtosWithLastMessage(new PageRequest(offset/limit, limit));
        if (chatDtos == null || chatDtos.getContent().isEmpty())
            throw new NoSuchEntityException(Chat.class.getName(), String.format("[offset: %d, limit: %d]", offset, limit));
        chatRepository.findAll(new PageRequest(offset/limit, limit));
        return chatDtos.getContent().stream()
                .peek(chat -> chat.setUnread(messageRepository.getUnreadCount(chat.getChatId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChatDto createChat(ChatDto chatDto) throws InputErrorException {
        if (chatRepository.findOne(chatDto.getChatId()) != null || chatDto.getChatId() == null)
            throw new InputErrorException(Chat.class.getName(), "chatId");
        Chat chat = modelMapper.map(chatDto, Chat.class);
        Chat createdChat = chatRepository.save(chat);
        return modelMapper.map(createdChat, ChatDto.class);
    }

    @Override
    @Transactional(rollbackFor=NoSuchEntityException.class)
    public ChatDto updateChat(ChatDto chatDto) throws NoSuchEntityException {
//        TODO check if a member of chat or admin
        if (chatRepository.findOne(chatDto.getChatId()) == null || chatDto.getChatId() == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId: " + chatDto.getChatId());
        Chat chat = modelMapper.map(chatDto, Chat.class);
        Chat updatedChat = chatRepository.saveAndFlush(chat);
        return modelMapper.map(updatedChat, ChatDto.class);
    }

    @Override
    @Transactional
    public void deleteChat(Long id)  throws NoSuchEntityException{
        if (chatRepository.findOne(id) == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId: " + id);
        chatRepository.delete(id);
    }

    @Override
    @Transactional
    public MessageDto postMessage(MessageDto messageDto) throws InputErrorException, NoSuchEntityException {
        if (messageDto.getMessageId() != 0)
            throw new InputErrorException(Message.class.getName(), "messageId");
        if (chatRepository.findOne(messageDto.getChat().getChatId()) == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId:" + messageDto.getChat().getChatId());
        Message message = modelMapper.map(messageDto, Message.class);
        Message createdMessage = messageRepository.saveAndFlush(message);
        return modelMapper.map(createdMessage, MessageDto.class);
    }

    @Override
    public Chat getChatByMember(Long id) {
        return null;
//        return chatsOfCurrentUser.stream().
//                filter(chat-> chat.getMembers().stream().
//                        anyMatch(user->user.getUserId().equals(id))).findFirst().get();
    }
}
