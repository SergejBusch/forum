package ru.job4j.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.forum.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
