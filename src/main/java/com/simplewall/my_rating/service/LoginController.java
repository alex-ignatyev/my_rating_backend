package com.simplewall.my_rating.service;

import com.simplewall.my_rating.entity.User;
import com.simplewall.my_rating.exception.RestException;
import com.simplewall.my_rating.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users")
    public List<User> getAllEmployees() {
        return usersRepository.findAll();
    }

    @GetMapping("/register")
    public ResponseEntity<User> register(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password
    ) {
        User user = usersRepository.findByNameOrLogin(name, login);
        if (user != null) {
            throw new RestException("User already exist", HttpStatus.CONFLICT);
        } else {
            User newUser = usersRepository.save(new User(name, login, password));
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<User> login(
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password
    ) {
        try {
            User user = usersRepository.findByLogin(login);
            if (Objects.equals(user.getPassword(), password)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                throw new RestException("Incorrect password", HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            throw new RestException("User not exist", HttpStatus.CONFLICT);
        }
    }
}
