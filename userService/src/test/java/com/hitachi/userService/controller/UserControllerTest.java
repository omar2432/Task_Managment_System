package com.hitachi.userService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.userService.common.MessageConstants;
import com.hitachi.userService.entity.User;
import com.hitachi.userService.exchange.request.UserReqDTO;
import com.hitachi.userService.exchange.response.UserResDTO;
import com.hitachi.userService.mapper.UserMapper;
import com.hitachi.userService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        UserReqDTO userReqDTO = new UserReqDTO("John Doe", "john.doe@example.com", 25, User.UserQualification.Engineer);
        User reqUser = new User("John Doe", "john.doe@example.com", 25, User.UserQualification.Engineer);
        User createdUser = new User(1L, "John Doe", "john.doe@example.com", 25, User.UserQualification.Engineer);
        UserResDTO userResDTO = new UserResDTO(1L, "John Doe", "john.doe@example.com", User.UserQualification.Engineer);

//        when(userMapper.mapToEntity(userReqDTO)).thenReturn(reqUser);
        when(userMapper.mapToEntity(any(UserReqDTO.class))).thenReturn(reqUser);
        when(userService.createUser(reqUser)).thenReturn(createdUser);
        when(userMapper.mapToDTO(createdUser)).thenReturn(userResDTO);

        mockMvc.perform(post(MessageConstants.API_BASE_PATH + MessageConstants.CREATE_USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userResDTO.getId()))
                .andExpect(jsonPath("$.name").value(userResDTO.getName()))
                .andExpect(jsonPath("$.email").value(userResDTO.getEmail()));

        verify(userService, times(1)).createUser(reqUser);
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User(1L, "John Doe", "john.doe@example.com", 25, User.UserQualification.Doctor);
        UserResDTO userResDTO = new UserResDTO(1L, "John Doe", "john.doe@example.com", User.UserQualification.Doctor);

        when(userService.getUserById(1L)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userResDTO);

        mockMvc.perform(get(MessageConstants.API_BASE_PATH + MessageConstants.GET_USER_BY_ID_PATH, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResDTO.getId()))
                .andExpect(jsonPath("$.name").value(userResDTO.getName()))
                .andExpect(jsonPath("$.email").value(userResDTO.getEmail()));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testUpdateUser() throws Exception {
        UserReqDTO userReqDTO = new UserReqDTO("John Updated", "updated@example.com", 30, User.UserQualification.Pilot);
        User user = new User(1L, "John Updated", "updated@example.com", 30, User.UserQualification.Pilot);
        UserResDTO userResDTO = new UserResDTO(1L, "John Updated", "updated@example.com", User.UserQualification.Pilot);

//        when(userMapper.mapToEntity(userReqDTO)).thenReturn(user);
        when(userMapper.mapToEntity(any(UserReqDTO.class))).thenReturn(user);
        when(userService.updateUser(1L, user)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userResDTO);

        mockMvc.perform(put(MessageConstants.API_BASE_PATH + MessageConstants.UPDATE_USER_PATH, 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userReqDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResDTO.getId()))
                .andExpect(jsonPath("$.name").value(userResDTO.getName()))
                .andExpect(jsonPath("$.email").value(userResDTO.getEmail()));

        verify(userService, times(1)).updateUser(1L, user);
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete(MessageConstants.API_BASE_PATH + MessageConstants.DELETE_USER_PATH, 1L))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
