package com.hitachi.userService.mapper;

import com.hitachi.userService.entity.User;
import com.hitachi.userService.exchange.request.UserReqDTO;
import com.hitachi.userService.exchange.response.UserResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Map UserReqDTO to User entity
    @Mapping(target = "id", ignore = true) // Ignore the ID during creation
    User mapToEntity(UserReqDTO userReqDTO);

    // Map User entity to UserResDTO
    UserResDTO mapToDTO(User user);

    // Update existing User entity from UserReqDTO
    @Mapping(target = "id", ignore = true) // Keep ID unchanged during updates
    void updateEntityFromEntity(User newUser, @MappingTarget User oldUser);
}
