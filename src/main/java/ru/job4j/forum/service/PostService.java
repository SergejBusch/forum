package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.PostRepository;
import ru.job4j.forum.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        postRepository.findAll().forEach(rsl::add);
        return rsl;
    }
}
