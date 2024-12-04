package com.malikov.study.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
@Data
@Validated
public class TaskDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private boolean completed;
}