package util;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    public static String sanitizeInput(String input) {
        if (input == null) return "";
        return input.trim();
    }

    public static boolean isValidName(String name) {
        if (name == null) return false;
        return name.length() >= 3 && name.length() <= 100;
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        String phoneRegex = "^[0-9]{10,20}$";
        return Pattern.matches(phoneRegex, phone);
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return password.length() >= 6;
    }
}