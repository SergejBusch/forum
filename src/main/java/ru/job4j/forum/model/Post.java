package ru.job4j.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Calendar;

@Data
@AllArgsConstructor(staticName = "of")
public class Post {

    private int id;
    private String name;
    private String desc;
    private Calendar created;
}
