package com.creato.beshka.web.api;

import com.creato.beshka.converters.View;
import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.exceptions.AuthRequiredException;
import com.creato.beshka.exceptions.EmailExistsException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.exceptions.ServiceErrorException;
import com.creato.beshka.services.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private final IUserService userService;

    @Autowired
    public UsersApiController(IUserService userService) {
        this.userService = userService;
    }

    @JsonView(View.Public.class)
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDto> getAllActiveUsers(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) throws NoSuchEntityException {
        return userService.getAllActiveUsers(offset, limit);
    }

    @JsonView(View.Public.class)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody UserDto userDto) throws EmailExistsException, ServiceErrorException {
        userService.addUser(userDto);
    }

    @JsonView(View.Public.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UserDto updateUser(@RequestBody UserDto userDto) throws NoSuchEntityException {
        return userService.updateUser(userDto);
    }

    @JsonView(View.Public.class)
    @RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
    public UserDto getCurrentUser() throws AuthRequiredException {
        return userService.getCurrentUser();
    }

}
