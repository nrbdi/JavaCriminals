package utils;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

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
}
