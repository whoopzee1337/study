package com.malikov.study.controller;

import com.malikov.study.dto.TaskDto;
import com.malikov.study.serivce.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("study-service/v1")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto createTask(@RequestBody @Valid TaskDto task) {
        return taskService.createTask(task);
    }

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> fetchTasks() {
        return taskService.fetchTasks();
    }

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto fetchTask(@PathVariable Long id) {
        return taskService.fetchTask(id);
    }

    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto updateTask(@PathVariable Long id,
                              @RequestBody @Valid TaskDto updatedTask) {
        updatedTask.setId(id);
        return taskService.updateTask(updatedTask);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
