package com.hitachi.userService.service;

import com.hitachi.userService.entity.User;
import com.hitachi.userService.exception.UserNotFoundException;
import com.hitachi.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }


    public User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with the ID "+id));
    }

    public User updateUser(long id,User userDetails){

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }

        User user = optionalUser.get();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setAge(userDetails.getAge());
        user.setQualification(userDetails.getQualification());

        return userRepository.save(user);
    }

    public void deleteUser(long id){

        userRepository.deleteById(id);
    }

    public void deleteUser(User user){

        userRepository.delete(user);
    }
}
