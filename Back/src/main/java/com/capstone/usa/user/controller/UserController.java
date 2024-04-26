package com.capstone.usa.user.controller;

import com.capstone.usa.user.dto.CreateUserDto;
import com.capstone.usa.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    public UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto dto) {
        return userService.loginUser(dto);
    }
}
