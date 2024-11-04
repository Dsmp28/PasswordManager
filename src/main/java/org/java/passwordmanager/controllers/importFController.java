package org.java.passwordmanager.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java.passwordmanager.deserializers.LocalDateTimeDeserializer;
import org.java.passwordmanager.encryption.DESEncryption;
import org.java.passwordmanager.objects.Registro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class importFController {

    private DESEncryption desEncrypt; // Instancia para manejar la encriptación DES

    public importFController() throws Exception {
        this.desEncrypt = new DESEncryption(archivosController.getDesPassword());
    }

    // Método para abrir el explorador de archivos y seleccionar un archivo .txt o .enc
    public void importarArchivo(File selectedFile) {
        if (selectedFile != null) {
            try {
                archivosController archivosController = new archivosController();
                // Procesa el archivo en función de su extensión
                if (selectedFile.getName().endsWith(".enc")) {
                    // Desencripta el contenido si el archivo es .enc
                    byte[] encryptedData = Files.readAllBytes(selectedFile.toPath());
                    byte[] decryptedData = desEncrypt.decrypt(encryptedData);
                    String jsonContent = new String(decryptedData);

                    // Convertir y guardar registros
                    Map<Integer, Registro> registros = convertirAHashMap(jsonContent);
                    archivosController.guardarRegistrosEnc(registros);
                } else if (selectedFile.getName().endsWith(".json")) {
                    String jsonContent = new String(Files.readAllBytes(selectedFile.toPath()));

                    // Convertir y guardar registros
                    Map<Integer, Registro> registros = convertirAHashMap(jsonContent);
                    archivosController.guardarRegistrosEnc(registros);
                } else {
                    System.out.println("Formato de archivo no compatible.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al procesar el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }

    // Método para convertir la lista "entries" en un HashMap con ID como clave
    private Map<Integer, Registro> convertirAHashMap(String jsonContent) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // Configurar el deserializador personalizado para LocalDateTime
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);

        // Leer el JSON y verificar si contiene "entries"
        ObjectNode rootNode = (ObjectNode) mapper.readTree(jsonContent);
        JsonNode entriesNode = rootNode.get("entries");

        Map<Integer, Registro> registrosMap = new HashMap<>();

        if (entriesNode == null || entriesNode.isNull()) {
            // Si "entries" es nulo, cargar el JSON como un HashMap directamente
            registrosMap = mapper.readValue(jsonContent, new TypeReference<Map<Integer, Registro>>() {});
        } else {
            // Si "entries" no es nulo, convertir la lista a un HashMap
            List<Registro> entries = mapper.convertValue(entriesNode, new TypeReference<List<Registro>>() {});
            for (Registro entry : entries) {
                registrosMap.put(entry.getId(), entry);
            }
        }

        return registrosMap;
    }

}

