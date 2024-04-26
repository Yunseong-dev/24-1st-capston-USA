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
        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()) != null) {
            throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
        }
        else {
            User user = new User(
                    dto.getName(),
                    dto.getPhoneNumber()
            );
            return userRepository.save(user);
        }
    }
}
