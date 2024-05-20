package com.capstone.usa.sms.controller;

import com.capstone.usa.sms.service.SmsService;
import com.capstone.usa.sms.dto.PhoneDto;
import lombok.AllArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class SmsController {
    private SmsService smsService;

    @PostMapping("/sms")
    public SingleMessageSentResponse sendSMS(@RequestBody PhoneDto dto) {
        return smsService.sendOne(dto);
    }
}
