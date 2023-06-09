package com.example.proyectofp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Decrypt {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    public static boolean verifyPassword(String password, String encryptedPassword) {
        try {
            // Convierte la contraseña encriptada de formato hexadecimal a bytes
            byte[] encryptedBytes = hexToBytes(encryptedPassword);

            // Extrae el salt de la contraseña encriptada
            byte[] salt = extractSalt(encryptedBytes);

            // Combina el salt con la contraseña ingresada
            byte[] saltedPassword = concatenateArrays(salt, password.getBytes());

            // Calcula el hash de la contraseña ingresada + salt
            byte[] hashedPassword = calculateHash(saltedPassword);

            // Obtiene el hash almacenado en la contraseña encriptada
            byte[] storedHash = extractHash(encryptedBytes);

            // Compara el hash calculado con el hash almacenado
            return MessageDigest.isEqual(hashedPassword, storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static byte[] extractSalt(byte[] encryptedBytes) {
        byte[] salt = new byte[SALT_LENGTH];
        System.arraycopy(encryptedBytes, 0, salt, 0, SALT_LENGTH);
        return salt;
    }

    private static byte[] extractHash(byte[] encryptedBytes) {
        byte[] hash = new byte[encryptedBytes.length - SALT_LENGTH];
        System.arraycopy(encryptedBytes, SALT_LENGTH, hash, 0, hash.length);
        return hash;
    }

    // Métodos auxiliares...

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

    private static byte[] hexToBytes(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
