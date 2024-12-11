package com.malikov.study.serivce;

import com.malikov.study.dto.TaskDto;
import com.malikov.study.kafka.TaskKafkaService;
import com.malikov.study.mapper.TaskMapper;
import com.malikov.study.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskKafkaService taskKafkaService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDto createTask(TaskDto taskDto) {
        return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
    }

    public List<TaskDto> fetchTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto fetchTask(Long id) {
        return taskMapper.toDto(taskRepository.getById(id));
    }

    public TaskDto updateTask(TaskDto taskDto) {
        taskKafkaService.sendMessage(taskDto);
        return taskMapper.toDto(taskRepository.save(taskMapper.toEntity(taskDto)));
    }

    public void deleteTask(Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID не может быть отрицательным!");
        }
        taskRepository.deleteById(id);
    }
}
