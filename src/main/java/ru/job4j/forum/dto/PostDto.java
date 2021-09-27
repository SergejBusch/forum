package ru.job4j.forum.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    private long id;
    @NotEmpty(message = "can not be empty")
    private String name;
    @NotEmpty(message = "can not be empty")
    private String description;
}
