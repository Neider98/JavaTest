package com.auto.inventory.autoinventory.services.Impl;
import com.auto.inventory.autoinventory.models.entities.User;
import com.auto.inventory.autoinventory.repositories.IUserRepository;
import com.auto.inventory.autoinventory.services.Impl.UserServiceImpl;
import com.auto.inventory.autoinventory.util.exceptions.IdNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindAllUsers() {

        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertEquals(users.size(), result.size());
        assertTrue(result.containsAll(users));
    }

    @Test
    void testFindUserById() {

        long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindUserByIdNotFound() {

        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    void testSaveUser() {

        User newUser = new User();
        when(userRepository.save(newUser)).thenReturn(newUser);

        User result = userService.saveUser(newUser);

        assertNotNull(result);
        assertEquals(newUser, result);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(1L);

        when(userRepository.findById(updatedUser.getId())).thenReturn(Optional.of(updatedUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser); // Mocking save behavior

        Optional<User> result = userService.updateUser(updatedUser);

        assertTrue(result.isPresent());
        assertEquals(updatedUser, result.get());
    }


    @Test
    void testDeleteUser() {

        long userId = 1L;
        User deletedUser = new User();
        deletedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(deletedUser));

        Optional<User> result = userService.deleteUser(userId);

        assertTrue(result.isPresent());
        assertEquals(deletedUser, result.get());
        verify(userRepository, times(1)).deleteById(userId);
    }
}

