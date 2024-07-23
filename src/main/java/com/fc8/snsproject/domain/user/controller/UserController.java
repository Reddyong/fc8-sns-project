package com.fc8.snsproject.domain.user.controller;

import com.fc8.snsproject.common.Response;
import com.fc8.snsproject.domain.user.dto.UserDto;
import com.fc8.snsproject.domain.user.dto.request.UserJoinRequest;
import com.fc8.snsproject.domain.user.dto.request.UserLoginRequest;
import com.fc8.snsproject.domain.user.dto.response.UserJoinResponse;
import com.fc8.snsproject.domain.user.dto.response.UserLoginResponse;
import com.fc8.snsproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/join")
    public Response<UserJoinResponse> join(
            @RequestBody UserJoinRequest userJoinRequest
    ) {
        UserDto userDto = userService.join(userJoinRequest.username(), userJoinRequest.password());
        UserJoinResponse userJoinResponse = UserJoinResponse.from(userDto);

        return Response.success(userJoinResponse);
    }

    @PostMapping(path = "/login")
    public Response<UserLoginResponse> login(
            @RequestBody UserLoginRequest userLoginRequest
    ) {
        String token = userService.login(userLoginRequest.username(), userLoginRequest.password());

        return Response.success(UserLoginResponse.of(token));
    }
}
