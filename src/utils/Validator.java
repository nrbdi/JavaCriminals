package utils;

import java.util.regex.Pattern;

public class Validator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zА-Яа-яЁё\\s]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(8|\\+7)\\d{10}$");
    private static final Pattern BRAND_MODEL_PATTERN = Pattern.compile("^[A-Za-z0-9]+$");
    private static final Pattern TYPE_COLOR_PATTERN = Pattern.compile("^[A-Za-zА-Яа-яЁё]+$");

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

    public static boolean isValidBrandOrModel(String input) {
        return input != null && BRAND_MODEL_PATTERN.matcher(input).matches();
    }

    public static boolean isValidTypeOrColor(String input) {
        return input != null && TYPE_COLOR_PATTERN.matcher(input).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    public static boolean isValidCameraOption(String input) {
        return input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("No");
    }

    public static boolean isValidCruiseControlOption(String input) {
        return input.equalsIgnoreCase("Standard") || input.equalsIgnoreCase("Adaptive") || input.equalsIgnoreCase("None");
    }

    public static boolean isValidAutopilotOption(String input) {
        return input.equalsIgnoreCase("Enabled") || input.equalsIgnoreCase("Disabled");
    }

    public static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-zА-Яа-яЁё\\s]+$");
    }
}
