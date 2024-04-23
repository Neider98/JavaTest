package com.auto.inventory.autoinventory.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.auto.inventory.autoinventory.facades.UserFacade;
import com.auto.inventory.autoinventory.models.dtos.PositionDTO;
import com.auto.inventory.autoinventory.models.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    private final UserFacade userFacade = mock(UserFacade.class);
    private final UserController userController = new UserController(userFacade);

    @Test
    void testGetUsers() {
        List<UserDTO> users = Arrays.asList(new UserDTO(1L, "User1", 30, new PositionDTO(1L, "Position1"), LocalDate.now()));
        when(userFacade.findAllUsers()).thenReturn(users);

        List<UserDTO> response = userController.getUsers();

        assertEquals(users, response);
        verify(userFacade, times(1)).findAllUsers();
    }

    @Test
    void testGetUserFound() {
        long userId = 1L;
        UserDTO user = new UserDTO(userId, "User1", 30, new PositionDTO(1L, "Position1"), LocalDate.now());
        when(userFacade.findUserById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<UserDTO> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        verify(userFacade, times(1)).findUserById(userId);
    }

    @Test
    void testGetUserNotFound() {
        long userId = 1L;
        when(userFacade.findUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.getUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userFacade, times(1)).findUserById(userId);
    }
}

