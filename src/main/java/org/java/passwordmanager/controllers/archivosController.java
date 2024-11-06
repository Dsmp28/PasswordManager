package org.java.passwordmanager.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.java.passwordmanager.encryption.DESEncryption;
import org.java.passwordmanager.encryption.RSAEncryption;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;
import org.java.passwordmanager.objects.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class archivosController {
    private static RSAEncryption rsaEnc;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String desDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/desData.json";
    private static final String userDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/userData.json";
    private static final String encryptedDataFile = "src/main/resources/org/java/passwordmanager/dataFiles/encryptedData.enc";
    public static Set<String> tags;

    public archivosController() {
        try {
            rsaEnc = new RSAEncryption();
            tags = new HashSet<>();
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

    public static Map<Integer, Registro> cargarRegistros() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Map<Integer, Registro> registros = new HashMap<>();
        try {
            File file = new File(encryptedDataFile);
            if (file.exists()) {
                byte[] encryptedBytes = Files.readAllBytes(Paths.get(encryptedDataFile));
                DESEncryption desEnc = new DESEncryption(getDesPassword());
                byte[] decryptedDataBytes = desEnc.decrypt(encryptedBytes);
                String jsonData = new String(decryptedDataBytes, StandardCharsets.UTF_8);
                registros = mapper.readValue(jsonData, new TypeReference<Map<Integer, Registro>>() {});
            }

            for (Registro registro : registros.values()){
                String encryptedPassword = registro.getPassword();
                String decryptedPassword = new RSAEncryption().decrypt(encryptedPassword);
                for (Tag tag : registro.getTags()) {
                    tags.add(tag.getName());
                }
                registro.setPassword(decryptedPassword);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los registros: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return registros;
    }

    // Guardar los registros en el archivo
    public void guardarRegistros(Map<Integer, Registro> registros) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            // Crear una copia temporal de los registros con las contraseñas encriptadas
            Map<Integer, Registro> registrosEncriptados = new HashMap<>();
            for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
                Registro registro = entry.getValue();
                String password = registro.getPassword();
                String encryptedPassword = new RSAEncryption().encrypt(password);
                registro.setPassword(encryptedPassword);
                registrosEncriptados.put(entry.getKey(), registro);
            }

            // Convertir `registrosEncriptados` a JSON y guardar en archivo
            File filePrueba = new File("src/main/resources/org/java/passwordmanager/dataFiles/PruebaPass.json");
            String jsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(registrosEncriptados);
            Files.write(Paths.get(encryptedDataFile), new DESEncryption(getDesPassword()).encrypt(jsonData));
            Files.write(filePrueba.toPath(), jsonData.getBytes());
        } catch (Exception e) {
            System.out.println("Error al guardar los registros: " + e.getMessage());
        }
    }

    // Guardar los registros en el archivo
    public void guardarRegistrosEnc(Map<Integer, Registro> registros) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            File filePrueba = new File("src/main/resources/org/java/passwordmanager/dataFiles/PruebaPass.json");
            String jsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(registros);
            Files.write(Paths.get(encryptedDataFile), new DESEncryption(getDesPassword()).encrypt(jsonData));
            Files.write(filePrueba.toPath(), jsonData.getBytes());
        } catch (Exception e) {
            System.out.println("Error al guardar los registros: " + e.getMessage());
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

    public static String getDesPassword() {
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


