package com.capstone.usa.user.dto;

import lombok.Getter;

@Getter
public class CreateUserDto {
    private String name;
    private String phoneNumber;
    private String verNumber;
    private String password;
}
