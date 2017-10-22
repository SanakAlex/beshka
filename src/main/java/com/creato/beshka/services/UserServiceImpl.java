package com.creato.beshka.services;

import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.exceptions.EmailExistsException;
import com.creato.beshka.exceptions.NoSuchEntityException;
import com.creato.beshka.exceptions.ServiceErrorException;
import com.creato.beshka.persistence.dao.UserRepository;
import com.creato.beshka.persistence.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SessionUtils sessionUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, SessionUtils sessionUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.sessionUtils = sessionUtils;
    }

    @Override
    @Transactional
    /**
     * offset number of rows to skip
     * limit max on request
     */
    public Map<String, Object> getAllActiveUsersWithUnreadCount(int offset, int limit) throws NoSuchEntityException {
        User currentUser = sessionUtils.getCurrentUser();
        PageRequest request = new PageRequest(offset/limit, limit);
        Page<UserDto> users = userRepository.findUserDtosWithReadCount(request, currentUser);
        Map<String, Object> map = new HashMap<>();
        if(users == null || users.getContent().isEmpty())
            throw new NoSuchEntityException(User.class.getName(), String.format("[offset: %d, limit: %d]", offset, limit));
        map.put("users", users.getContent());
        map.put("currentUser", currentUser);
        return map;
    }

    @Override
    @Transactional
    public List<UserDto> getAllActiveUsers(int offset, int limit) throws NoSuchEntityException {
        Page<User> users = userRepository.findAllByActiveIsTrue(new PageRequest(offset/limit, limit));
        if(users == null || users.getContent().isEmpty())
            throw new NoSuchEntityException(User.class.getName(), String.format("[offset: %d, limit: %d]", offset, limit));
        return users.getContent().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) throws EmailExistsException, ServiceErrorException {
        if (userRepository.findByEmail(userDto.getEmail()) != null)
            throw new EmailExistsException();
        User user = modelMapper.map(userDto, User.class);
        user = userRepository.saveAndFlush(user);
        if (user == null)
            throw new ServiceErrorException();
    }

    @Override
    @Transactional(rollbackFor = NoSuchEntityException.class)
    public UserDto updateUser(UserDto userDto) throws NoSuchEntityException {
        if (userRepository.findOne(userDto.getUserId()) == null)
            throw new NoSuchEntityException(User.class.getName(), "userId: " + userDto.getUserId());
        User user = modelMapper.map(userDto, User.class);
        user = userRepository.saveAndFlush(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto getCurrentUser() {
        User user = sessionUtils.getCurrentUser();
        return modelMapper.map(user, UserDto.class);
    }
}
