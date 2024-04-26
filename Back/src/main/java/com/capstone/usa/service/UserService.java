package com.capstone.usa.service;

import com.capstone.usa.dto.CreateUserDto;
import com.capstone.usa.model.User;
import com.capstone.usa.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User createUser(CreateUserDto dto) {
        User user = new User(
                dto.getName(),
                dto.getPhoneNumber()
        );
        return userRepository.save(user);
    }
}
