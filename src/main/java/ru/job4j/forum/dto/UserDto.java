package ru.job4j.forum.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {

    @NotEmpty(message = "can not be empty")
    private String email;
    @NotEmpty(message = "can not be empty")
    private String name;
    @NotEmpty(message = "can not be empty")
    private String password;

}
