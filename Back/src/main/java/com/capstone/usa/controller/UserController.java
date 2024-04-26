package com.capstone.usa.controller;

import com.capstone.usa.dto.CreateUserDto;
import com.capstone.usa.dto.PhoneDto;
import com.capstone.usa.model.User;
import com.capstone.usa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    public UserService userService;

    @PostMapping("/signup")
    public User createUser(@RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }
}
