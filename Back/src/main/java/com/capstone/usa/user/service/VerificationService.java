package com.capstone.usa.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificationService {
    private static Map<String, String> verificationCodes = new HashMap<>();

    public static String GenerateNumber(String phoneNumber){
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String verificationCode = String.valueOf(randomNumber);

        verificationCodes.put(phoneNumber, verificationCode);

        return verificationCode;
    }

    public static String getVerificationCode(String phoneNumber) {
        return verificationCodes.get(phoneNumber);
    }
}
