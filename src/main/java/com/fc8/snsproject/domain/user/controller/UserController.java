package com.fc8.snsproject.domain.user.controller;

import com.fc8.snsproject.common.Response;
import com.fc8.snsproject.domain.alarm.dto.AlarmDto;
import com.fc8.snsproject.domain.alarm.dto.response.AlarmResponse;
import com.fc8.snsproject.domain.user.dto.UserDto;
import com.fc8.snsproject.domain.user.dto.request.UserJoinRequest;
import com.fc8.snsproject.domain.user.dto.request.UserLoginRequest;
import com.fc8.snsproject.domain.user.dto.response.UserJoinResponse;
import com.fc8.snsproject.domain.user.dto.response.UserLoginResponse;
import com.fc8.snsproject.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/alarms")
    public Response<Page<AlarmResponse>> alarm(
            Pageable pageable,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<AlarmDto> alarmDtoPage = userService.alarmList(userDetails.getUsername(), pageable);
        Page<AlarmResponse> alarmResponsePage = alarmDtoPage.map(AlarmResponse::from);

        return Response.success(alarmResponsePage);
    }
}
