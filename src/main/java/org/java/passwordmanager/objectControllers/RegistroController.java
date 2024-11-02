package org.java.passwordmanager.objectControllers;

import org.java.passwordmanager.objects.CamposExtra;
import org.java.passwordmanager.objects.Icon;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class RegistroController {

    public static Map<Integer, Registro> registros = new HashMap<>(); // <id, Registro>


    public static void addRegistro(Registro registro) {
        registros.put(registro.getId(), registro);
    }

    public static void removeRegistro(Registro registro) {
        registros.remove(registro.getId());
    }

    public static void editRegistro(Registro registroViejo, String siteName, String username, String password, String url, String notes, CamposExtra camposExtra, List<Tag> tags, LocalDateTime expirationDate,Icon icon) {
       if(registros.containsKey(registroViejo.getId())) {
            registroViejo.setSiteName(siteName);
            registroViejo.setUsername(username);
            registroViejo.setPassword(password);
            registroViejo.setUrl(url);
            registroViejo.setNotes(notes);
            registroViejo.setCamposExtra(camposExtra);
            registroViejo.setTags(tags);
            registroViejo.setUpdateDate(LocalDateTime.now());
            registroViejo.setExpirationDate(expirationDate);
            registroViejo.setIcon(icon);
       }else{
              System.out.println("Registro no encontrado.");
       }
    }

    public static void changeExpirationDates(LocalDateTime newExpirationDate) {
        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            entry.getValue().setExpirationDate(newExpirationDate);
        }
    }

    public void changeExpirationDate(Registro registro, LocalDateTime newExpirationDate) {
        if (registros.containsKey(registro.getId())) {
            registros.get(registro.getId()).setExpirationDate(newExpirationDate);
        } else {
            System.out.println("Registro no encontrado.");
        }
    }

    public static Registro getRegistro(int id) {
        return registros.get(id);
    }
    public static int getTotalRegistros() {
        return registros.size();
    }
    public static Map<Integer, Registro> getRegistros() {
        return registros;
    }

    public static int getSize() {
        return registros.size();
    }

    ////////////////////////////////////////// BUSQUEDAS ///////////////////////////////////////////////

    public static List<Registro> searchBySiteName(String siteName) {
        List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getSiteName().equals(siteName)) {
                registrosEncontrados.add(entry.getValue());
            }
        }
        return registrosEncontrados;
    }

    public static List<Registro> searchByUsername(String username) {
        List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                registrosEncontrados.add(entry.getValue());
            }
        }
        return registrosEncontrados;
    }

    public static List<Registro> searchByURL(String url) {
        List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getUrl().equals(url)) {
                registrosEncontrados.add(entry.getValue());
            }
        }
        return registrosEncontrados;
    }

    public static List<Registro> searchByNotes(String notes) {
        List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getNotes().equals(notes)) {
                registrosEncontrados.add(entry.getValue());
            }
        }
        return registrosEncontrados;
    }

    public static List<Registro> searchByCreationDate(LocalDate creationDate) {
         List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getCreationDate().toLocalDate().equals(creationDate)) {
                registrosEncontrados.add(entry.getValue());
            }
        }

        return registrosEncontrados;
    }

    public static List<Registro> searchByUpdateDate(LocalDate updateDate) {
         List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getUpdateDate().toLocalDate().equals(updateDate)) {
                registrosEncontrados.add(entry.getValue());
            }
        }

        return registrosEncontrados;
    }

    public static List<Registro> searchByExpirationDate(LocalDate expirationDate) {
         List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getExpirationDate().toLocalDate().equals(expirationDate)) {
                registrosEncontrados.add(entry.getValue());
            }
        }

        return registrosEncontrados;
    }

    public static List<Registro> searchByExtraField(String campoExtra) {
        List<Registro> registrosEncontrados = new ArrayList<>();

        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            CamposExtra camposExtra = entry.getValue().getCamposExtra();
            List<String> extras = camposExtra.getExtras();
            for (String extra : extras) {
                if (extra.equals(campoExtra)) {
                    registrosEncontrados.add(entry.getValue());
                    break;
                }
            }
        }
        return registrosEncontrados;
    }



    public static void mostrarRegistrosEncontrados(List<Registro> registrosEncontrados) {
        for (Registro registro : registrosEncontrados) {
            System.out.println(registro);
        }
        System.out.println("\n");
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    //Prueba
    public static void mostrarRegistros() {
        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
    //Fin prueba
}
