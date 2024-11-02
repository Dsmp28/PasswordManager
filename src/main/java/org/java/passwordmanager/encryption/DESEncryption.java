package org.java.passwordmanager.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class DESEncryption {
    private static final String DES_ALGORITHM = "DES";
    private static final String DES_TRANSFORMATION = "DES/ECB/NoPadding"; // NoPadding as we handle padding manually
    private final SecretKeySpec desKey;

    // Constructor that sets the DES key
    public DESEncryption(String key) throws Exception {
        byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 8); // Ensure 8-byte key for DES
        this.desKey = new SecretKeySpec(keyBytes, DES_ALGORITHM);
    }

    // Method for encrypting with PKCS7 padding
    public byte[] encrypt(String data) throws Exception {
        byte[] dataBytes = addPkcs7Padding(data.getBytes(StandardCharsets.UTF_8), 8);
        Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        return cipher.doFinal(dataBytes); // Return raw encrypted bytes
    }

    // Method for decrypting and removing PKCS7 padding
    public byte[] decrypt(byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return removePkcs7Padding(decryptedData); // Return raw decrypted bytes
    }

    // Add PKCS7 padding
    private byte[] addPkcs7Padding(byte[] data, int blockSize) {
        int paddingSize = blockSize - (data.length % blockSize);
        byte[] paddedData = Arrays.copyOf(data, data.length + paddingSize);
        Arrays.fill(paddedData, data.length, paddedData.length, (byte) paddingSize);
        return paddedData;
    }

    // Remove PKCS7 padding
    private byte[] removePkcs7Padding(byte[] data) {
        int paddingSize = data[data.length - 1];
        if (paddingSize < 1 || paddingSize > data.length) {
            throw new IllegalArgumentException("Invalid padding size");
        }
        return Arrays.copyOfRange(data, 0, data.length - paddingSize);
    }

    // Main method for testing
    public static void main(String[] args) throws Exception {
        String key = "ok:uo1IN";  // 8-byte DES key
        String originalText = "{\"isbn\":\"9781839046407\",\"name\":\"Walk set design deal next off sell foot reach car official skin current charge bank someone daughter process attorney collection likely name prove simply themselves.\",\"author\":\"Thomas Jackson MD\",\"category\":\"Romance\",\"price\":\"92.05\",\"quantity\":\"460\"}\r\n" +
                "{\"isbn\":\"9781147811407\",\"name\":\"Dark environmental more appear.\",\"author\":\"Gloria Garcia\",\"category\":\"Science Fiction\",\"price\":\"60.72\",\"quantity\":\"498\"}\r\n" +
                "{\"isbn\":\"9780856397349\",\"name\":\"Then go hope attention friend peace create each.\",\"author\":\"Eric Fleming\",\"category\":\"Biography\",\"price\":\"74.60\",\"quantity\":\"690\"}\r\n";

        DESEncryption desEncryption = new DESEncryption(key);

        // Encrypt the message
        byte[] encryptedData = desEncryption.encrypt(originalText);
        File encryptedDataF = new File("D:/Proyectos/ProyectosJava/PasswordManager/src/main/resources/org/java/passwordmanager/dataFiles/encryptedData.enc");
        Files.write(encryptedDataF.toPath(), encryptedData);
        System.out.println("Encrypted (raw bytes): " + Arrays.toString(encryptedData));

        // Decrypt the message
        byte[] decryptedData = desEncryption.decrypt(encryptedData);
        System.out.println("Decrypted text: " + new String(decryptedData, StandardCharsets.UTF_8));
    }
}



