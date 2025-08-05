package com.shop.online.utils;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Random;

public final class StringRanDom extends StringUtils {

    private static final Random RANDOM_METHOD = new Random();



    public static String generateCode(int len) {
        // Using numeric values
        String numbers = "0123456789";
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            otp[i] = numbers.charAt(RANDOM_METHOD.nextInt(numbers.length()));
        }


        return String.valueOf(otp);
    }

    public static boolean isPhoneNumber(String number) {
        return number.matches("^\\+?\\d{1,3}\\d{9,15}$");
    }


    @SuppressWarnings("java:S5361")
    public static String formatSpaceString(String data) {
        if (data == null) return "";
        data = data.replaceAll("\t", "");
        data = data.replaceAll("\n", "");
        data = data.replaceAll(" ", "");
        return data;
    }

    public static boolean isNullOrEmpty(Object cs) {
        return ObjectUtils.isEmpty(cs);
    }

    /**
     * safe equal
     *
     * @param obj1 String
     * @param obj2 String
     * @return boolean
     */
    public static boolean safeEqual(String obj1, String obj2) {
        return obj1.equals(obj2);
    }

    /**
     * safe equal
     *
     * @param obj1 Integer
     * @param obj2 Integer
     * @return boolean
     */
    public static boolean safeEqual(Integer obj1, Integer obj2) {
        if (obj1.equals(obj2)) return true;
        return obj2 != null && obj1.compareTo(obj2) == 0;
    }
}
