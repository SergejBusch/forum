package ru.job4j.forum.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.dto.UserDto;
import ru.job4j.forum.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = "/registration")
    public String register(@Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userDto);
            return "registration";
        }

        try {
            service.register(userDto);
        } catch (BadCredentialsException exception) {
            bindingResult.rejectValue("email", "userDto.email",
                    "An account already exists for this email.");
            model.addAttribute("registrationForm", userDto);
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String register(final Model model) {
        model.addAttribute("userDto", new UserDto());
        return "/registration";
    }
}
