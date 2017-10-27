package com.creato.beshka.web.api;

import com.creato.beshka.converters.View;
import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.converters.dto.MessageDto;
import com.creato.beshka.exceptions.AuthRequiredException;
import com.creato.beshka.exceptions.InputErrorException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.services.IChatService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/chats")
public class ChatApiController {

    private final IChatService chatService;

    @Autowired
    public ChatApiController(IChatService chatService) {
        this.chatService = chatService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(View.WithoutMessages.class)
    public List<ChatDto> getAllChatsByUserIn(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) throws NoSuchEntityException, AuthRequiredException {
        return chatService.getChatsOfCurrentUser(offset, limit);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.WithMessages.class)
    public ChatDto createChat(@RequestBody ChatDto chatDto) throws InputErrorException {
        return chatService.createChat(chatDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(View.WithMessages.class)
    public ChatDto getChat(@PathVariable Long id) throws NoSuchEntityException {
        return chatService.getChatById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.WithMessages.class)
    public ChatDto updateChat(@RequestBody ChatDto chatDto) throws NoSuchEntityException {
        return chatService.updateChat(chatDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable Long id) throws NoSuchEntityException {
        chatService.deleteChat(id);
    }

    @RequestMapping(value = "/{id}/post", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.WithMessages.class)
    public MessageDto postMessage(@RequestBody MessageDto messageDto) throws NoSuchEntityException, InputErrorException {
        return chatService.postMessage(messageDto);
    }

    @RequestMapping(value = "/{id}/read", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void readMessagesInChat(@PathVariable Long id) throws AuthRequiredException, NoSuchEntityException {
        chatService.readMessages(id);
    }

    @RequestMapping(value = "/withUser/{id}")
    @JsonView(View.WithMessages.class)
    public ChatDto getChatWithUser(@PathVariable Long id) throws NoSuchEntityException, AuthRequiredException {
        return chatService.getChatWithUser(id);
    }

    @MessageMapping("/{id}/post")
    @SendTo("/topic/chats/{id}")
    public MessageDto send(@DestinationVariable("id") Long chatId, MessageDto messageDto)
            throws NoSuchEntityException, InputErrorException {
        return chatService.postMessage(messageDto);
    }

    @MessageMapping("/{id}")
    @SendTo("/topic/chats/{id}")
    public ChatDto getChatWS(@DestinationVariable("id") Long chatId)
            throws NoSuchEntityException, InputErrorException {
        return chatService.getChatById(chatId);
    }

    @MessageMapping
    @SendTo("/topic/chats")
    public List<ChatDto> getChatsWS(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) throws NoSuchEntityException, AuthRequiredException {
        return chatService.getChatsOfCurrentUser(offset, limit);
    }
}
