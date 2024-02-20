package com.simplewall.my_rating.controller;

import com.simplewall.my_rating.model.entity.User;
import com.simplewall.my_rating.model.request.user.ForgotRequest;
import com.simplewall.my_rating.model.request.user.LoginRequest;
import com.simplewall.my_rating.model.request.user.RegisterRequest;
import com.simplewall.my_rating.model.response.UserResponse;
import com.simplewall.my_rating.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user_info")
    public ResponseEntity<UserResponse> getUserInfo(
            @RequestParam String login
    ) {
        return userService.getUserInfo(login);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return userService.register(registerRequest);
    }

    @PostMapping("/forgot")
    public ResponseEntity<User> forgot(
            @RequestBody ForgotRequest forgotRequest
    ) {
        return userService.forgot(forgotRequest);
    }
}
