package com.hitachi.userService.service;

import com.hitachi.userService.common.MessageConstants;
import com.hitachi.userService.entity.User;
import com.hitachi.userService.exception.UserNotFoundException;
import com.hitachi.userService.mapper.UserMapper;
import com.hitachi.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Autowired
    UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }


    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(MessageConstants.USER_NOT_FOUND +id));
    }

    public User updateUser(long id,User userDetails){

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format(MessageConstants.USER_NOT_FOUND_WITH_ID, id));
        }

        User user = optionalUser.get();

        userMapper.updateEntityFromEntity(userDetails,user);

        return userRepository.save(user);
    }

    public void deleteUser(long id){
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(MessageConstants.USER_NOT_FOUND +id);
        }
        userRepository.deleteById(id);
    }
}
