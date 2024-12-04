package com.hitachi.taskService.mapper;

import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.exchange.request.TaskReqDTO;
import com.hitachi.taskService.exchange.response.TaskResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    // Map UserReqDTO to User entity
    @Mapping(target = "id", ignore = true) // Ignore the ID during creation
    Task mapToEntity(TaskReqDTO userReqDTO);

    // Map User entity to UserResDTO
    TaskResDTO mapToDTO(Task user);

    // Update existing User entity from UserReqDTO
    @Mapping(target = "id", ignore = true) // Keep ID unchanged during updates
    void updateEntityFromDto(TaskReqDTO userReqDTO, @MappingTarget Task user);


}
