package ru.job4j.forum.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.job4j.forum.model.User;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MyUserDetails {

    @Bean
    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Arrays.stream(user.getAuthorities()
                        .split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
