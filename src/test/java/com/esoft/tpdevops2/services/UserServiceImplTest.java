package com.esoft.tpdevops2.services;

import com.esoft.tpdevops2.entities.User;
import com.esoft.tpdevops2.repositories.UserRepository;
import com.esoft.tpdevops2.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUser_shouldReturnSavedUser() {
        User user = new User();
        user.setId(100L);
        user.setFirstName("Ameni");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Ameni", savedUser.getFirstName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        User user1 = new User();
        User user2 = new User();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_shouldReturnUser() {
        User user = new User();
        user.setId(100L);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
        verify(userRepository).findById(1);
    }

    @Test
    void updateUser_shouldUpdateWhenUserExists() {
        User user = new User();
        user.setId(100L);
        user.setFirstName("Updated");

        when(userRepository.existsById(1)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(1, user);

        assertNotNull(updatedUser);
        assertEquals("Updated", updatedUser.getFirstName());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_shouldReturnNullWhenUserDoesNotExist() {
        User user = new User();

        when(userRepository.existsById(1)).thenReturn(false);

        User result = userService.updateUser(1, user);

        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_shouldCallRepository() {
        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
