package org.java.passwordmanager.objectControllers;

import org.java.passwordmanager.objects.CamposExtra;
import org.java.passwordmanager.objects.Icon;
import org.java.passwordmanager.objects.Registro;
import org.java.passwordmanager.objects.Tag;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<Integer, Registro> getRegistros() {
        return registros;
    }

    public static int getSize() {
        return registros.size();
    }

    //Prueba
    public static void mostrarRegistros() {
        for (Map.Entry<Integer, Registro> entry : registros.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
    //Fin prueba
}
