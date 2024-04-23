package com.auto.inventory.autoinventory.facades;

import com.auto.inventory.autoinventory.mappers.UserMapper;
import com.auto.inventory.autoinventory.models.dtos.UserDTO;
import com.auto.inventory.autoinventory.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserFacade {

    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserFacade(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> findUserById(Long id) {
        return userMapper.mapToDtoOptional(userService.findUserById(id));
    }

    public List<UserDTO> findAllUsers() {
        return userMapper.mapToDtoList(userService.findAllUsers());
    }
}
