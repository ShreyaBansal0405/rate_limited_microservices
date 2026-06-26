package com.shreya.auth_service.controller;

import com.shreya.auth_service.model.User;
import com.shreya.auth_service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/test")
    public List<User> test() {
        return userRepository.findAll();
    }
}