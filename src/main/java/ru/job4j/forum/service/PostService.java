package ru.job4j.forum.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.job4j.forum.dto.PostDto;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.repository.PostRepository;

import javax.persistence.EntityExistsException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAll() {
        return new ArrayList<>(postRepository.findAll());
    }

    public PostDto findById(long id) throws ChangeSetPersister.NotFoundException {
        var post = postRepository.findById(id).orElseThrow(
                ChangeSetPersister.NotFoundException::new);
        var postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setName(post.getName());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    public void update(PostDto postDto) throws ChangeSetPersister.NotFoundException {
        var post = postRepository.findById(postDto.getId()).orElseThrow(
                ChangeSetPersister.NotFoundException::new);
        saveHelper(postDto, post);
    }

    public void save(PostDto postDto) {
        var post = new Post();
        post.setCreated(Calendar.getInstance());
        saveHelper(postDto, post);
    }

    private void saveHelper(PostDto postDto, Post post) {
        post.setDescription(postDto.getDescription());
        post.setName(postDto.getName());
        postRepository.save(post);
    }
}
