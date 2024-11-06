package org.java.passwordmanager.controllers;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.java.passwordmanager.encryption.DESEncryption;
import org.java.passwordmanager.encryption.RSAEncryption;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.format.DateTimeFormatter;

public class exportFController {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public exportFController() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void exportRegistros(Map<Integer, Registro> registros, String outputFile, String type) throws Exception {
        List<ObjectNode> entriesList = new ArrayList<>();

        // Configuración del formato de fecha y hora con zona horaria
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");

        // Convertir cada registro a un nodo JSON en formato de lista
        for (Registro registro : registros.values()) {
            ObjectNode entryNode = objectMapper.createObjectNode();

            // Agregar campos básicos
            entryNode.put("id", registro.getId());
            entryNode.put("site_name", registro.getSiteName());
            entryNode.put("username", registro.getUsername());
            if (type.equals("enc")) {
                entryNode.put("password", new RSAEncryption().encrypt(registro.getPassword()));
            } else {
                entryNode.put("password", registro.getPassword());
            }
            entryNode.put("url", registro.getUrl());
            entryNode.put("notes", registro.getNotes());

            // Agregar extra fields antes de tags
            ObjectNode extraFieldsNode = objectMapper.createObjectNode();
            extraFieldsNode.put("extra1", registro.getCamposExtra().getExtra1());
            extraFieldsNode.put("extra2", registro.getCamposExtra().getExtra2());
            extraFieldsNode.put("extra3", registro.getCamposExtra().getExtra3());
            extraFieldsNode.put("extra4", registro.getCamposExtra().getExtra4());
            extraFieldsNode.put("extra5", registro.getCamposExtra().getExtra5());
            entryNode.set("extra_fields", extraFieldsNode);

            // Convertir tags a una lista de strings y añadirlos después de extra_fields
            ArrayNode tagsArray = objectMapper.createArrayNode();
            for (Tag tag : registro.getTags()) {
                tagsArray.add(tag.getName());
            }
            entryNode.set("tags", tagsArray);

            // Convertir las fechas a OffsetDateTime y formatearlas
            ZoneOffset offset = ZoneOffset.of("-06:00");
            entryNode.put("creation_date", registro.getCreationDate().atOffset(offset).format(dateFormatter));
            entryNode.put("update_date", registro.getUpdateDate().atOffset(offset).format(dateFormatter));
            entryNode.put("expiration_date", registro.getExpirationDate().atOffset(offset).format(dateFormatter));

            entryNode.put("icon", registro.getIcon().getImagen());

            // Añadir a la lista de entries
            entriesList.add(entryNode);
        }

        // Crear el nodo raíz y agregar la lista de "entries"
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode entriesArray = rootNode.putArray("entries");
        entriesArray.addAll(entriesList);

        // Configuración de pretty print personalizada para JSON con ajuste manual en la salida
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\n"));

        // Convertir a JSON
        String jsonData = objectMapper.writer(prettyPrinter).writeValueAsString(rootNode);

        // Ajuste manual: remover espacios entre el campo y los dos puntos, pero dejar espacio entre los dos puntos y el valor
        jsonData = jsonData.replaceAll("\"(\\w+)\"\\s*:\\s*", "\"$1\": ");

        Path path = Paths.get(outputFile);

        if (type.equals("json")) {
            // Guardar el archivo JSON sin encriptar
            Files.writeString(path, jsonData);
        } else if (type.equals("enc")) {
            // Encriptar y guardar el archivo
            byte[] encryptedData = new DESEncryption(archivosController.getDesPassword()).encrypt(jsonData);
            Files.write(path, encryptedData);
        }
    }

    public void exportRegistrosSeleccionados(List<Registro> registros, String outputFile, String type) throws Exception {
        List<ObjectNode> entriesList = new ArrayList<>();

        // Configuración del formato de fecha y hora con zona horaria
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");

        // Convertir cada registro a un nodo JSON en formato de lista
        for (Registro registro : registros) {
            ObjectNode entryNode = objectMapper.createObjectNode();

            // Agregar campos básicos
            entryNode.put("id", registro.getId());
            entryNode.put("site_name", registro.getSiteName());
            entryNode.put("username", registro.getUsername());
            if (type.equals("enc")) {
                entryNode.put("password", new RSAEncryption().encrypt(registro.getPassword()));
            } else {
                entryNode.put("password", registro.getPassword());
            }
            entryNode.put("url", registro.getUrl());
            entryNode.put("notes", registro.getNotes());

            // Agregar extra fields antes de tags
            ObjectNode extraFieldsNode = objectMapper.createObjectNode();
            extraFieldsNode.put("extra1", registro.getCamposExtra().getExtra1());
            extraFieldsNode.put("extra2", registro.getCamposExtra().getExtra2());
            extraFieldsNode.put("extra3", registro.getCamposExtra().getExtra3());
            extraFieldsNode.put("extra4", registro.getCamposExtra().getExtra4());
            extraFieldsNode.put("extra5", registro.getCamposExtra().getExtra5());
            entryNode.set("extra_fields", extraFieldsNode);

            // Convertir tags a una lista de strings y añadirlos después de extra_fields
            ArrayNode tagsArray = objectMapper.createArrayNode();
            for (Tag tag : registro.getTags()) {
                tagsArray.add(tag.getName());
            }
            entryNode.set("tags", tagsArray);

            // Convertir las fechas a OffsetDateTime y formatearlas
            ZoneOffset offset = ZoneOffset.of("-06:00");
            entryNode.put("creation_date", registro.getCreationDate().atOffset(offset).format(dateFormatter));
            entryNode.put("update_date", registro.getUpdateDate().atOffset(offset).format(dateFormatter));
            entryNode.put("expiration_date", registro.getExpirationDate().atOffset(offset).format(dateFormatter));

            entryNode.put("icon", registro.getIcon().getImagen());

            // Añadir a la lista de entries
            entriesList.add(entryNode);
        }

        // Crear el nodo raíz y agregar la lista de "entries"
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode entriesArray = rootNode.putArray("entries");
        entriesArray.addAll(entriesList);

        // Configuración de pretty print personalizada para JSON con ajuste manual en la salida
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        prettyPrinter.indentObjectsWith(new DefaultIndenter("  ", "\n"));

        // Convertir a JSON
        String jsonData = objectMapper.writer(prettyPrinter).writeValueAsString(rootNode);

        // Ajuste manual: remover espacios entre el campo y los dos puntos, pero dejar espacio entre los dos puntos y el valor
        jsonData = jsonData.replaceAll("\"(\\w+)\"\\s*:\\s*", "\"$1\": ");

        Path path = Paths.get(outputFile);

        if (type.equals("json")) {
            // Guardar el archivo JSON sin encriptar
            Files.writeString(path, jsonData);
        } else if (type.equals("enc")) {
            // Encriptar y guardar el archivo
            byte[] encryptedData = new DESEncryption(archivosController.getDesPassword()).encrypt(jsonData);
            Files.write(path, encryptedData);
        }
    }


}
