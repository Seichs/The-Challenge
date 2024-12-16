package com.example.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    // Genereer een willekeurig salt voor hashing
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash het wachtwoord met een gegeven salt
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Valideer een wachtwoord door het te hashen met dezelfde salt en vergelijken met de opgeslagen hash
    public static boolean validatePassword(String password, String salt, String storedHash) {
        String hashedPassword = hashPassword(password, salt);
        return hashedPassword.equals(storedHash);
    }
}
