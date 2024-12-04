package com.hitachi.userService.controller;

import com.hitachi.userService.common.MessageConstants;
import com.hitachi.userService.entity.User;
import com.hitachi.userService.exchange.request.UserReqDTO;
import com.hitachi.userService.exchange.response.UserResDTO;
import com.hitachi.userService.mapper.UserMapper;
import com.hitachi.userService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(MessageConstants.API_BASE_PATH)
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    @Autowired
    public UserController(UserService userService, UserMapper userMapper){
        this.userService=userService;
        this.userMapper = userMapper;
    }

    @PostMapping(MessageConstants.CREATE_USER_PATH)
    public ResponseEntity<UserResDTO> createUser(@RequestBody @Valid UserReqDTO userReqDTO){
        User reqUser=userMapper.mapToEntity(userReqDTO);
        User createdUser = userService.createUser(reqUser);
        UserResDTO userResDTO=userMapper.mapToDTO(createdUser);
        return new ResponseEntity<UserResDTO>(userResDTO, HttpStatus.CREATED);
    }


    @GetMapping(MessageConstants.GET_USER_BY_ID_PATH)
    public ResponseEntity<UserResDTO> getUserById(@PathVariable long id){
        User user=userService.getUserById(id);
        UserResDTO userResDTO=userMapper.mapToDTO(user);
        return ResponseEntity.ok(userResDTO);
    }


    @PutMapping(MessageConstants.UPDATE_USER_PATH)
    public ResponseEntity<UserResDTO> updateUser(@PathVariable long id,@RequestBody @Valid UserReqDTO userReqDTO){
        User reqUser=userMapper.mapToEntity(userReqDTO);
        User updatedUser = userService.updateUser(id,reqUser);
        UserResDTO userResDTO=userMapper.mapToDTO(updatedUser);
        return ResponseEntity.ok(userResDTO);
    }

    @DeleteMapping(MessageConstants.DELETE_USER_PATH)
    public ResponseEntity<Void> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
