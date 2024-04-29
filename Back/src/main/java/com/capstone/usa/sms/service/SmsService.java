package com.capstone.usa.sms.service;

import com.capstone.usa.user.dto.PhoneDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final String from;
    private final DefaultMessageService messageService;

    public SmsService(@Value("${coolsms.apiKey}")
                      String apiKey,
                      @Value("${coolsms.apiSecret}")
                      String secretKey,
                      @Value("${coolsms.from}")
                      String from) {
        this.from = from;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendOne(PhoneDto dto) {
        String VerificationCode = VerificationService.GenerateNumber(dto.getPhoneNumber());
        String text = "[USA]인증번호는 " + VerificationCode + " 입니다.";

        Message message = new Message();
        message.setFrom(from);
        message.setTo(dto.getPhoneNumber());
        message.setText(text);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}

