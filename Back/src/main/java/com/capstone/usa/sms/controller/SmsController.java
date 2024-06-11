package com.capstone.usa.sms.controller;

import com.capstone.usa.sms.dto.PhoneDto;
import com.capstone.usa.sms.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class SmsController {
    private SmsService smsService;

    @PostMapping("/sms")
    public ResponseEntity<String> sendSMS(@RequestBody PhoneDto dto) {
        return smsService.sendOne(dto);
    }
}
