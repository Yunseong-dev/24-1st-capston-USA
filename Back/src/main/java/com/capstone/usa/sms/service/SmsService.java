package com.capstone.usa.sms.service;

import com.capstone.usa.sms.dto.PhoneDto;
import com.capstone.usa.auth.repository.UserRepository;
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

    public SmsService(@Value("${aligo.apiKey}") String apiKey,
                      @Value("${aligo.userId}") String id,
                      @Value("${aligo.sender}") String sender,
                      UserRepository userRepository) {

        this.apiKey = apiKey;
        this.id = id;
        this.sender = sender;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> sendOne(PhoneDto dto) {
        if(userRepository.findById(dto.getPhoneNumber()).isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 등록된 전화번호 입니다");
        }
        else {
            String smsUrl = "https://apis.aligo.in/send/";

            Map<String, String> sms = new HashMap<>();
            sms.put("user_id", id);
            sms.put("key", apiKey);

            String VerificationCode = VerificationService.GenerateNumber(dto.getPhoneNumber());
            sms.put("msg", "[USA]인증번호는 " + VerificationCode + " 입니다.");
            sms.put("receiver", dto.getPhoneNumber());
            sms.put("sender", sender);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            for (Map.Entry<String, String> entry : sms.entrySet()) {
                body.add(entry.getKey(), entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            org.springframework.http.HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new org.springframework.http.HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(smsUrl, requestEntity, String.class);

            System.out.println(response.getBody());
        }
        return ResponseEntity.status(HttpStatus.OK).body("인증번호가 전송되었습니다");
    }
}
