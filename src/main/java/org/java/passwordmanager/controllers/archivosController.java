package org.java.passwordmanager.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.passwordmanager.encryption.DESEncryption;
import org.java.passwordmanager.encryption.RSAEncryption;
import org.java.passwordmanager.objects.User;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class archivosController {
    private RSAEncryption rsaEnc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String desDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/desData.json";
    private static final String userDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/userData.json";
    private static final String encryptedDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/encryptedData.json";

    public archivosController() {
        try {
            rsaEnc = new RSAEncryption();
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar las llaves RSA", e);
        }
    }

    // Método para guardar datos de usuario
    public void saveUserData(String username, String password) {
        try {
            List<User> users = loadUsers();

            String encryptedUsername = rsaEnc.encrypt(username);
            String encryptedPassword = rsaEnc.encrypt(password);

            User newUser = new User(encryptedUsername, encryptedPassword);
            users.add(newUser);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(userDataFile), users);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los datos del usuario", e);
        }
    }

    // Método para guardar datos de encriptación DES
    public void saveDesData(String data) {
        try {
            String encryptedData = rsaEnc.encrypt(data);

            // Crear un Map con la clave encriptada
            Map<String, String> desDataMap = new HashMap<>();
            desDataMap.put("encryptedKey", encryptedData);

            // Guardar en JSON usando el Map
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(desDataFile), desDataMap);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los datos DES", e);
        }
    }

    public void updateDesPassword(String newPassword) {
        try {
            DESEncryption desEnc = new DESEncryption(getDesPassword());

            File file = new File(encryptedDataFile);
            String oldEncryptedData = objectMapper.readValue(file, new TypeReference<String>() {});

            byte[] decryptedData = desEnc.decrypt(oldEncryptedData.getBytes());

            desEnc.setKey(newPassword);

            byte[] newEncryptedData = desEnc.encrypt(Arrays.toString(decryptedData));

            Files.write(file.toPath(), newEncryptedData);

            // Crear un Map con la clave encriptada
            Map<String, String> desDataMap = new HashMap<>();
            desDataMap.put("encryptedKey", rsaEnc.encrypt(newPassword));

            // Guardar en JSON usando el Map
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(desDataFile), desDataMap);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los datos DES", e);
        }
    }

    public boolean isAlreadyDesKey() {
        try {
            File file = new File(desDataFile);
            if (!file.exists()) {
                return false;
            }

            Map<String, String> desDataMap = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
            String encryptedKey = desDataMap.get("encryptedKey");

            return encryptedKey != null;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar los datos DES", e);
        }
    }

    public boolean isUserRegistered(String username){
        try {
            List<User> users = loadUsers();
            for (User user : users) {
                String decryptedUsername = rsaEnc.decrypt(user.getUsername());
                if (decryptedUsername.equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar los datos del usuario", e);
        }
    }

    public User getUser(String username, String password) {
        try {
            List<User> users = loadUsers();
            for (User user : users) {
                String decryptedUsername = rsaEnc.decrypt(user.getUsername());
                String decryptedPassword = rsaEnc.decrypt(user.getPassword());
                if (decryptedUsername.equals(username) && decryptedPassword.equals(password)) {
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los datos del usuario", e);
        }
    }

    public String getDesPassword() {
        try {
            File file = new File(desDataFile);
            Map<String, String> desDataMap = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
            String encryptedKey = desDataMap.get("encryptedKey");
            return rsaEnc.decrypt(encryptedKey);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la clave DES", e);
        }
    }

    // Método para cargar la lista de usuarios desde el archivo userData.json
    private List<User> loadUsers() {
        File file = new File(userDataFile);
        try {
            return objectMapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}


