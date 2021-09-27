package ru.job4j.forum.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import ru.job4j.forum.ForumApplication;
import ru.job4j.forum.config.H2TestProfileJPAConfig;
import ru.job4j.forum.dto.PostDto;
import ru.job4j.forum.service.PostService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = {ForumApplication.class, H2TestProfileJPAConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControlTest {

    @Autowired
    PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("posts"));

    }

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnAnotherMessage() throws Exception {
        var post = new PostDto();
        post.setName("name");
        post.setDescription("desc");
        postService.save(post);
        this.mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnYetMessage() throws Exception {
        this.mockMvc.perform(get("/posts/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }
 }
