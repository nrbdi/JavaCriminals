package utils;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zА-Яа-яЁё]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+7|8)\\d{10}$");
    private static final Pattern BRAND_MODEL_PATTERN = Pattern.compile("^[A-Za-zА-Яа-я0-9\\- ]+$");

    public static boolean isValidBrandOrModel(String input) {
        return input != null && BRAND_MODEL_PATTERN.matcher(input).matches();
    }


    public static boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isPositiveNumber(double number) {
        return number >= 0;
    }

    public static boolean isPositiveInteger(int number) {
        return number > 0;
    }

    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }
}
