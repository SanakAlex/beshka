package com.creato.beshka.services;

import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.exceptions.EmailExistsException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.exceptions.ServiceErrorException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IUserService {
    @Transactional
    /**
     * offset number of rows to skip
     * limit max on request
     */
    Map<String, Object> getAllActiveUsersWithUnreadCount(int offset, int limit) throws NoSuchEntityException;

    List<UserDto> getAllActiveUsers(int offset, int limit) throws NoSuchEntityException;

    void addUser(UserDto userDto) throws EmailExistsException, ServiceErrorException;

    UserDto updateUser(UserDto userDto) throws NoSuchEntityException;

    @Transactional
    UserDto getCurrentUser();
}
