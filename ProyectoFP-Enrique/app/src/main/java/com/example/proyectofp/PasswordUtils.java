package com.example.proyectofp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public static String encryptPassword(String password) {
        try {
            // Genera un salt aleatorio
            byte[] salt = generateSalt();

            // Combina el salt con la contraseña
             byte[] saltedPassword = concatenateArrays(salt, password.getBytes());

            // Calcula el hash de la contraseña + salt
            byte[] hashedPassword = calculateHash(saltedPassword);

            // Combina el salt y el hash en una sola cadena
            byte[] encryptedPassword = concatenateArrays(salt, hashedPassword);

            // Convierte la cadena en formato hexadecimal para almacenarla o enviarla
            return bytesToHex(encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] calculateHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        return digest.digest(data);
    }

    private static byte[] concatenateArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
