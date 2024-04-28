package com.capstone.usa.user.service;

import com.capstone.usa.sms.service.VerificationService;
import com.capstone.usa.user.dto.CreateUserDto;
import com.capstone.usa.user.dto.LoginUserDto;
import com.capstone.usa.user.model.User;
import com.capstone.usa.user.repository.UserRepository;
import com.capstone.usa.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createUser(CreateUserDto dto) {
        VerificationService.testCode();
        String savedVerificationCode = VerificationService.getVerificationCode(dto.getPhoneNumber());

        if(userRepository.findById(dto.getPhoneNumber()).isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 등록된 전화번호 입니다");
        }
        else{
            if (savedVerificationCode != null && savedVerificationCode.equals(dto.getVerNumber())) {
                User user = new User(dto.getName(), dto.getPhoneNumber(), dto.getPassword());
                userRepository.save(user);
                VerificationService.deleteVerificationCode(dto.getPhoneNumber());

                return ResponseEntity.ok().body("사용자가 성공적으로 생성되었습니다");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다");
            }
        }
    }

    public ResponseEntity<?> loginWithAuthenticationManager(LoginUserDto dto) {
        var request = new UsernamePasswordAuthenticationToken(
                dto.getPhoneNumber(), dto.getPassword()
        );

        var result = authenticationManager.authenticate(request);
        return ResponseEntity.ok().body(jwtUtil.generateToken((User) result.getPrincipal()));
    }
}
