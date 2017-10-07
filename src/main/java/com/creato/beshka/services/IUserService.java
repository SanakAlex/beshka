package com.creato.beshka.services;

import com.creato.beshka.persistence.entities.User;

import java.util.List;

public interface IUserService {
    List<User> getAllActiveUsers();
    void addUser(User user);
    User updateUser(User user);
}
