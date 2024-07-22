package com.fc8.snsproject.domain.user.controller;

import com.fc8.snsproject.domain.user.entity.User;
import com.fc8.snsproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    // TODO : implement
    @PostMapping(path = "/join")
    public void join() {
        userService.join("", "");
    }

    @PostMapping(path = "/login")
    public void login() {

    }
}
