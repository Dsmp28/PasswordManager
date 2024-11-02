package org.java.passwordmanager.encryption;

import io.github.cdimascio.dotenv.Dotenv;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryption {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RSAEncryption() throws Exception {
        Dotenv dotenv = Dotenv.load(); // Carga las variables de entorno desde .env
        String privateKeyPEM = dotenv.get("RSA_PRIVATE_KEY");
        String publicKeyPEM = dotenv.get("RSA_PUBLIC_KEY");

        X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(publicKeyPEM));
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(privateKeyPEM));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        this.publicKey = keyFactory.generatePublic(keySpecPublic);
        this.privateKey = keyFactory.generatePrivate(keySpecPrivate);
    }

    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    public void printKeys(){
        System.err.println("Public key\n"+ encode(publicKey.getEncoded()));
        System.err.println("Private key\n"+ encode(privateKey.getEncoded()));
    }

    public String encrypt(String message) throws Exception {
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);

        // Remueve los bytes nulos iniciales
        int startIndex = 0;
        while (startIndex < decryptedMessage.length && decryptedMessage[startIndex] == 0) {
            startIndex++;
        }
        byte[] trimmedMessage = new byte[decryptedMessage.length - startIndex];
        System.arraycopy(decryptedMessage, startIndex, trimmedMessage, 0, trimmedMessage.length);

        return new String(trimmedMessage, "UTF8");
    }


    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}


