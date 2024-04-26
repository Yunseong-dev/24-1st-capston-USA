package com.capstone.usa.user.controller;

import com.capstone.usa.user.dto.CreateUserDto;
import com.capstone.usa.user.dto.LoginUserDto;
import com.capstone.usa.user.dto.PhoneDto;
import com.capstone.usa.user.dto.TokenDto;
import com.capstone.usa.user.model.User;
import com.capstone.usa.user.service.SmsService;
import com.capstone.usa.user.service.UserService;
import lombok.AllArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    public UserService userService;
    public SmsService smsService;


    @PostMapping("/signup")
    public User createUser(@RequestBody CreateUserDto dto) {
        return userService.createUser(dto);
    }

    @PostMapping("/signin")
    public TokenDto loginUser(@RequestBody LoginUserDto dto){
        return userService.loginWithAuthenticationManager(dto);
    }

    @PostMapping("/SMS")
    public SingleMessageSentResponse sendSMS(@RequestBody PhoneDto dto) {
        return smsService.sendOne(dto);
    }
}
