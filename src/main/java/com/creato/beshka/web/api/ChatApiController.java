package com.creato.beshka.web.api;

import com.creato.beshka.converters.View;
import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.services.IChatService;
import com.creato.beshka.persistence.entities.Chat;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatApiController {

    private final IChatService chatService;


    @Autowired
    public ChatApiController(IChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * offset number of rows to skip
     * limit max on request
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<ChatDto> getAllChatsByUserIn(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) throws NoSuchEntityException {
        return chatService.getChatsByUserIn(new PageRequest(offset/limit, limit));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView(View.UI.class)
    public ChatDto createChat(@RequestBody ChatDto chatDto){
        return chatService.createChat(chatDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @JsonView(View.UI.class)
    public ChatDto getChat(@PathVariable Long id) throws NoSuchEntityException {
        return chatService.getChatById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(View.UI.class)
    public ChatDto updateChat(@RequestBody ChatDto chatDto) throws NoSuchEntityException {
        return chatService.updateChat(chatDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteChat(@PathVariable Long id) throws NoSuchEntityException {
        chatService.deleteChat(id);
    }

//    @RequestMapping(value = "writeTo/{id}", method = RequestMethod.GET)
//    public ChatDto writeToUser(@PathVariable Long id){
//        return chatService.getChatByMember(id);
//    }
//
//    @RequestMapping(value = "{id}/messages", method = RequestMethod.GET)
//    public List<Message> getChatsMessages(@PathVariable Long id) {
//        return chatService.getChatsMessages(id);
//    }
}
