package com.capstone.usa.service;

import com.capstone.usa.dto.PhoneDto;
import com.capstone.usa.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final UserRepository userRepository;
    DefaultMessageService messageService;

    @Value("${spring.coolsms.apiKey}")
    private String apiKey;

    @Value("${spring.coolsms.apiSecret}")
    private String secretkey;

    @Value("${spring.coolsms.senderNumber}")
    private String to;


    public void ExampleController() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretkey, "https://api.coolsms.co.kr");
    }

    public SmsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SingleMessageSentResponse sendOne(PhoneDto dto){
        if (userRepository.findByPhoneNumber(dto.getPhoneNumber()) != null) {
            throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
        }
        else{
            Message message = new Message();
            message.setFrom(dto.getPhoneNumber());
            message.setTo(to);
            message.setText("[USA]인증번호는 0000 입니다.");

            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println(response);

            return response;
        }
    }
}
