package org.java.passwordmanager.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.passwordmanager.encryption.RSAEncryption;
import org.java.passwordmanager.objects.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class archivosController {
    private RSAEncryption rsaEnc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String desDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/desData.json";
    private static final String userDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/userData.json";

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


