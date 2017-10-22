package com.creato.beshka.services;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.InputErrorException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.persistence.dao.ChatRepository;
import com.creato.beshka.persistence.dao.MessageRepository;
import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.Message;
import com.creato.beshka.persistence.entities.User;
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
    private final SessionUtils sessionUtils;

    @Autowired
    public ChatServiceImpl(ModelMapper modelMapper, ChatRepository chatRepository, MessageRepository messageRepository, SessionUtils sessionUtils) {
        this.modelMapper = modelMapper;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.sessionUtils = sessionUtils;
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
        PageRequest pageRequest = new PageRequest(offset/limit, limit);
        User currentUser = sessionUtils.getCurrentUser();
        Page<Chat> chats = chatRepository.getChatWithLastMessage(pageRequest, currentUser);

        if (chats == null || chats.getContent().isEmpty())
            throw new NoSuchEntityException(Chat.class.getName(), String.format("[offset: %d, limit: %d]", offset, limit));
        return chats.getContent().stream()
                .map(this::getChatDtoWithLastMessage)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ChatDto createChat(ChatDto chatDto) throws InputErrorException {
        if (chatDto.getChatId() != null)
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
        if (messageDto.getMessageId() != null)
            throw new InputErrorException(Message.class.getName(), "messageId");
        if (chatRepository.findOne(messageDto.getChat().getChatId()) == null)
            throw new NoSuchEntityException(Chat.class.getName(), "chatId:" + messageDto.getChat().getChatId());
        Message message = modelMapper.map(messageDto, Message.class);
        Message createdMessage = messageRepository.saveAndFlush(message);
        return modelMapper.map(createdMessage, MessageDto.class);
    }

    @Override
    @Transactional
    public void readMessages(Long id) {
        List<Message> messages = messageRepository.findByReadIsFalseAndChat_ChatId(id);
        messages.forEach(message -> {
            message.setRead(true);
            messageRepository.save(message);
        });
    }

    @Override
    public Chat getChatByMember(Long id) {
        return null;
//        return chatsOfCurrentUser.stream().
//                filter(chat-> chat.getMembers().stream().
//                        anyMatch(user->user.getUserId().equals(id))).findFirst().get();
    }

    @Override
    public ChatDto getChatDtoWithLastMessage(Chat chat) {
        ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
        Message lastMessage = chat.getMessages().get(chat.getMessages().size() - 1);
        chatDto.setLastMessage(modelMapper.map(lastMessage, MessageDto.class));
        chatDto.setUnread(messageRepository.getUnreadCount(chat.getChatId()));
        return chatDto;
    }
}
