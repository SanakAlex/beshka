package com.creato.beshka.web.api;

import com.creato.beshka.persistence.entities.User;
import com.creato.beshka.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private final IUserService userService;

    @Autowired
    public UsersApiController(IUserService userService) {
        this.userService = userService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllActiveUsers(){
        return userService.getAllActiveUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody User user){
        userService.addUser(user);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

}
