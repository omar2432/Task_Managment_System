package com.hitachi.userService.mapper;

import com.hitachi.userService.entity.User;
import com.hitachi.userService.exchange.request.UserReqDTO;
import com.hitachi.userService.exchange.response.UserResDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void mapToDTO_shouldMapEntityToResDTO(){
        User user = new User(1L,"John", "john@example.com",21,User.UserQualification.Engineer);
        UserResDTO userResDTO= userMapper.mapToDTO(user);
        assertEquals(user.getId(),userResDTO.getId());
        assertEquals(user.getName(),userResDTO.getName());
        assertEquals(user.getQualification(),userResDTO.getQualification());
    }

    @Test
    public void mapToEntity_shouldMapReqDTOToUserEntity(){
        UserReqDTO userReqDTO=new UserReqDTO("John", "john@example.com",21,User.UserQualification.Engineer);
        User user=userMapper.mapToEntity(userReqDTO);

        assertEquals(userReqDTO.getName(),user.getName());
        assertEquals(userReqDTO.getEmail(),user.getEmail());
        assertEquals(userReqDTO.getAge(),user.getAge());
    }

    @Test
    public void updateEntityFromEntity_shouldUpdateTheExistingUserEntity_withoutChangingTheId(){
        User existingUser=new User(5L,"John", "john@example.com",21,User.UserQualification.Engineer);
        User newUser=new User(6L,"newJohn", "newjohn@example.com",22,User.UserQualification.Engineer);
        userMapper.updateEntityFromEntity(newUser,existingUser);

        assertNotEquals(existingUser.getId(),newUser.getId());
        assertEquals(existingUser.getId(),5L);
        assertEquals(existingUser.getName(),newUser.getName());
        assertEquals(existingUser.getEmail(),newUser.getEmail());
    }

}
