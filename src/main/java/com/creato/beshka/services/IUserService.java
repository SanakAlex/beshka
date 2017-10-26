package com.creato.beshka.services;

import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.exceptions.AuthRequiredException;
import com.creato.beshka.exceptions.EmailExistsException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.exceptions.ServiceErrorException;

import java.util.List;

public interface IUserService {
    List<UserDto> getAllActiveUsers(int offset, int limit) throws NoSuchEntityException;

    void addUser(UserDto userDto) throws EmailExistsException, ServiceErrorException;

    UserDto updateUser(UserDto userDto) throws NoSuchEntityException;

    UserDto getCurrentUser() throws AuthRequiredException;
}
