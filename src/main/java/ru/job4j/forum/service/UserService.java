package ru.job4j.forum.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.forum.dto.UserDto;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void register(UserDto user) throws AuthenticationException {
        if (checkIfUserExist(user.getEmail())) {
            throw new BadCredentialsException("User exist already");
        }
        var userEntity = new User();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        encodePassword(userEntity, user);
        userEntity.setAuthorities("read");
        userRepository.save(userEntity);
    }

    private boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private void encodePassword(User user, UserDto userDto) {
        user.setPassword(encoder.encode(userDto.getPassword()));
    }
}
