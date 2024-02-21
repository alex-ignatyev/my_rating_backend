package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.request.user.ForgotRequest;
import com.simplewall.my_rating.model.request.user.LoginRequest;
import com.simplewall.my_rating.model.request.user.RegisterRequest;
import com.simplewall.my_rating.model.response.UserResponse;
import com.simplewall.my_rating.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    @GetMapping("/user_info")
    public ResponseEntity<UserResponse> getUserInfo(
            @RequestParam String login
    ) {
        return userService.getUserInfo(login);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return userService.register(registerRequest);
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(
            @RequestBody ForgotRequest forgotRequest
    ) {
        return userService.forgot(forgotRequest);
    }
}
