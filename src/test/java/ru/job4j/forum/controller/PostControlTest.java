package ru.job4j.forum.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.ForumApplication;
import ru.job4j.forum.config.H2TestProfileJPAConfig;
import ru.job4j.forum.dto.PostDto;
import ru.job4j.forum.service.PostService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = {ForumApplication.class, H2TestProfileJPAConfig.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControlTest {

    @Autowired
    PostService postService;

    @MockBean
    PostService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Profile("test")
    @WithMockUser(authorities = "write")
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("posts"));

    }

    @WithMockUser(authorities = "write")
    public void shouldReturnAnotherMessage() throws Exception {
        PostDto post = new PostDto();
        post.setName("name");
        post.setDescription("desc");
        long id = postService.save(post);

        this.mockMvc.perform(get("/post/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnYetMessage() throws Exception {
        this.mockMvc.perform(get("/post/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnAnyMessage() throws Exception {
        this.mockMvc.perform(post("/post/new")
                .param("name","Car")
                .param("description","good Car"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<PostDto> argument = ArgumentCaptor.forClass(PostDto.class);
        verify(service).save(argument.capture());
        assertThat(argument.getValue().getName(), is("Car"));
    }

    @Test
    @WithMockUser(authorities = "write")
    public void shouldReturnAnyMessage2() throws Exception {
        this.mockMvc.perform(post("/post/{1}", 1)
                .param("id", "1")
                .param("name","Car")
                .param("description","good Car"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<PostDto> argument = ArgumentCaptor.forClass(PostDto.class);
        verify(service).update(argument.capture());
        assertThat(argument.getValue().getName(), is("Car"));
    }
 }
