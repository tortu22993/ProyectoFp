package com.example.proyectofp;/*package com.example.proyectofp;
/*
public class CaesarCipher {
    private static final int SHIFT = 3; // Número de posiciones a desplazar

    public static String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);

            if (Character.isLetter(ch)) {
                // Aplica el desplazamiento a las letras
                encryptedMessage.append(shiftCharacter(ch, SHIFT));
            } else if (Character.isDigit(ch)) {
                // Aplica el desplazamiento a los dígitos numéricos
                encryptedMessage.append(shiftCharacter(ch, SHIFT % 10));
            } else {
                // Conserva los caracteres no alfabéticos sin cambios
                encryptedMessage.append(ch);
            }
        }

        return encryptedMessage.toString();
    }

    public static String decrypt(String encryptedMessage) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char ch = encryptedMessage.charAt(i);

            if (Character.isLetter(ch)) {
                // Deshace el desplazamiento de las letras
                decryptedMessage.append(shiftCharacter(ch, -SHIFT));
            } else if (Character.isDigit(ch)) {
                // Deshace el desplazamiento de los dígitos numéricos
                decryptedMessage.append(shiftCharacter(ch, -(SHIFT % 10)));
            } else {
                // Conserva los caracteres no alfabéticos sin cambios
                decryptedMessage.append(ch);
            }
        }

        return decryptedMessage.toString();
    }

    private static char shiftCharacter(char ch, int shift) {
        if (Character.isUpperCase(ch)) {
            return (char) ((ch - 'A' + shift + 26) % 26 + 'A');
        } else if (Character.isLowerCase(ch)) {
            return (char) ((ch - 'a' + shift + 26) % 26 + 'a');
        } else {
            return ch;
        }
    }

}*/