package com.malikov.study.mapper;

import com.malikov.study.dto.TaskDto;
import com.malikov.study.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDto taskDto);

    TaskDto toDto(Task taskEntity);
}
