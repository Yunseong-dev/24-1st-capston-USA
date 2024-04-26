package com.capstone.usa.user.service;

import com.capstone.usa.sms.service.VerificationService;
import com.capstone.usa.user.dto.CreateUserDto;
import com.capstone.usa.user.model.User;
import com.capstone.usa.user.repository.UserRepository;
import com.capstone.usa.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseEntity<?> loginUser(CreateUserDto dto) {
        Map<String, Object> response = new HashMap<>();
        String savedVerificationCode = VerificationService.getVerificationCode(dto.getPhoneNumber());

        if (savedVerificationCode != null && savedVerificationCode.equals(dto.getVerNumber())) {
            User user = new User(dto.getName(), dto.getPhoneNumber());
            userRepository.save(user);
            VerificationService.deleteVerificationCode(dto.getPhoneNumber());

            String token = jwtUtil.generateToken(user);

            response.put("token", token);
            response.put("message", "사용자가 성공적으로 생성되었습니다.");

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
        }
    }
}
