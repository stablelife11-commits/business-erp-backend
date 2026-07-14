package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.entity.User;
import com.student.studentmanagementapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.saveUser(user);

    }

}