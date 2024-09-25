package com.tallerfinal.tallerfinal.controller;

import com.tallerfinal.tallerfinal.model.User;
import com.tallerfinal.tallerfinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> updateUserBalance(@PathVariable String id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        return userService.updateUserBalance(id, amount);
    }
}
