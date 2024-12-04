package com.hitachi.userService.service;

import com.hitachi.userService.entity.User;
import com.hitachi.userService.exception.UserNotFoundException;
import com.hitachi.userService.mapper.UserMapper;
import com.hitachi.userService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private User userWithOutId;
    private User userWithId;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userWithOutId = new User("John", "john@example.com",21,User.UserQualification.Engineer);
        userWithId = new User(1L,"John", "john@example.com",21,User.UserQualification.Engineer);
    }

    @Test
    public void createUser_ShouldReturnUser() {
        Mockito.when(userRepository.save(userWithOutId)).thenReturn(userWithId);

        User result = userService.createUser(userWithOutId);

        assertNotNull(result);
        assertEquals(userWithId.getId(), result.getId());
        assertEquals(userWithId.getName(), result.getName());
    }

    @Test
    public void getUserById_UserExists_ShouldReturnUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userWithId));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(userWithId.getId(), result.getId());
    }

    @Test
    public void getUserById_UserNotFound_ShouldThrowException() {
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(2L));
    }

    @Test
    public void updateUser_UserExists_ShouldReturnUpdatedUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userWithId));
        Mockito.when(userRepository.save(userWithId)).thenReturn(userWithId);

        User result = userService.updateUser(1L, userWithId);

        assertNotNull(result);
        assertEquals(userWithId.getId(), result.getId());
    }

    @Test
    public void deleteUser_UserNotFound_ShouldThrowException() {
        Mockito.when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(2L));
    }

    @Test
    public void deleteUser_UserExists_ShouldDeleteUser() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }
}
