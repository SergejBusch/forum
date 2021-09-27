package ru.job4j.forum.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.dto.PostDto;
import ru.job4j.forum.service.PostService;

import javax.validation.Valid;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService posts) {
        this.postService = posts;
    }

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getAll());
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String get(@PathVariable int id, Model model) {
        PostDto postDto = null;
        try {
            postDto = postService.findById(id);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }
        model.addAttribute("postDto", postDto);
        return "edit";
    }

    @PostMapping("/posts/{id}")
    public String update(@PathVariable int id, @Valid PostDto postDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        try {
            postService.update(postDto);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/new")
    public String createNew(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "create";
    }

    @PostMapping("/posts/new")
    public String save(@Valid PostDto postDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        postService.save(postDto);
        return "redirect:/posts";
    }
}
