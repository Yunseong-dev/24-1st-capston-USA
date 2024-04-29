package com.capstone.usa.sms.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificationService {
    private static final Map<String, String> verificationCodes = new HashMap<>();

    public static String GenerateNumber(String phoneNumber){
        Random random = new Random();
        int randomNumber = random.nextInt(1000, 10000);
        String verificationCode = String.valueOf(randomNumber);

        verificationCodes.put(phoneNumber, verificationCode);

        return verificationCode;
    }

    public static String getVerificationCode(String phoneNumber) {
        return verificationCodes.get(phoneNumber);
    }

    public static void deleteVerificationCode(String phoneNumber) {
        verificationCodes.remove(phoneNumber);
    }

    public static void testCode(){
        verificationCodes.put("01048158219", "0000");
    }
}
