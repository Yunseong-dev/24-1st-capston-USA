package com.capstone.usa.sms.service;

import com.capstone.usa.user.dto.PhoneDto;
import com.capstone.usa.user.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private String apiKey;
    private String secretKey;
    private String to;
    private final UserRepository userRepository;
    private final DefaultMessageService messageService;

    public SmsService(@Value("${coolsms.apiKey}")
                      String apiKey,
                      @Value("${coolsms.apiSecret}")
                      String secretKey,
                      @Value("${coolsms.to}")
                      String to,
                      UserRepository userRepository) {
        this.userRepository = userRepository;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.to = to;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendOne(PhoneDto dto) {
        String VerificationCode = VerificationService.GenerateNumber(dto.getPhoneNumber());
        String text = "[USA]인증번호는 " + VerificationCode + " 입니다.";

        Message message = new Message();
        message.setFrom(dto.getPhoneNumber());
        message.setTo(to);
        message.setText(text);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}

