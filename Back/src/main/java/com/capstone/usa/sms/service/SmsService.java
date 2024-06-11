package com.capstone.usa.sms.service;

import com.capstone.usa.sms.dto.PhoneDto;
import com.capstone.usa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
    private final String apiKey;
    private final String id;
    private final String sender;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public SmsService(@Value("${aligo.apiKey}") String apiKey,
                      @Value("${aligo.userId}") String id,
                      @Value("${aligo.sender}") String sender,
                      UserRepository userRepository,
                      RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.id = id;
        this.sender = sender;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendOne(PhoneDto dto) {
        if (userRepository.findById(dto.getPhoneNumber()).isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 등록된 전화번호 입니다");
        }

        String smsUrl = "https://apis.aligo.in/send/";
        String verificationCode = VerificationService.GenerateNumber(dto.getPhoneNumber());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("user_id", id);
        body.add("key", apiKey);
        body.add("msg", "[USA] 인증번호 발송알림 인증번호는 " + verificationCode + " 입니다.");
        body.add("receiver", dto.getPhoneNumber());
        body.add("sender", sender);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(smsUrl, requestEntity, String.class);

        System.out.println(response.getBody());

        return ResponseEntity.status(HttpStatus.OK).body("인증번호가 전송되었습니다");
    }
}
