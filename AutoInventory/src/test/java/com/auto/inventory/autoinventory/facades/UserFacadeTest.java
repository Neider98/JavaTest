package com.auto.inventory.autoinventory.facades;

import com.auto.inventory.autoinventory.facades.UserFacade;
import com.auto.inventory.autoinventory.mappers.UserMapper;
import com.auto.inventory.autoinventory.models.dtos.UserDTO;
import com.auto.inventory.autoinventory.models.entities.User;
import com.auto.inventory.autoinventory.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    @Mock
    private IUserService userService;

    @Mock
    private UserMapper userMapper;

    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userFacade = new UserFacade(userService, userMapper);
    }

    @Test
    void testFindUserById() {
        Long userId = 1L;
        User user = new User();
        when(userService.findUserById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapToDtoOptional(Optional.of(user))).thenReturn(Optional.of(new UserDTO()));

        Optional<UserDTO> result = userFacade.findUserById(userId);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindAllUsers() {
        List<User> users = Collections.singletonList(new User());
        when(userService.findAllUsers()).thenReturn(users);
        when(userMapper.mapToDtoList(users)).thenReturn(Collections.singletonList(new UserDTO()));

        List<UserDTO> result = userFacade.findAllUsers();

        assertEquals(1, result.size());
    }
}

