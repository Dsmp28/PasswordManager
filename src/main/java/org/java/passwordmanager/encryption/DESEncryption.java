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
    private SecretKeySpec desKey;

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

    public void setKey(String key) {
        byte[] keyBytes = Arrays.copyOf(key.getBytes(StandardCharsets.UTF_8), 8); // Asegura clave de 8 bytes para DES
        this.desKey = new SecretKeySpec(keyBytes, DES_ALGORITHM);
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

    public static void main(String[] args) {
        try {
            File file = new File("/Users/davidmonje/Documents/ProyectosPersonalesLocal/PasswordManager/src/main/resources/org/java/passwordmanager/dataFiles/salidaEncriptada.enc");
            DESEncryption desEnc = new DESEncryption("12345678");
            byte[] encryptedData = desEnc.encrypt("{}");
            Files.write(file.toPath(), encryptedData);
            System.out.println(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



