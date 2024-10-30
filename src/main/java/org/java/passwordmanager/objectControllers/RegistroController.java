package org.java.passwordmanager.objectControllers;

import org.java.passwordmanager.objects.Registro;

import java.util.HashMap;
import java.util.Map;

public class RegistroController {

    private Map<Integer, Registro> registros = new HashMap<>(); // <id, Registro>


    public void addRegistro(Registro registro) {
        registros.put(registro.getId(), registro);
    }

    public void removeRegistro(Registro registro) {
        registros.remove(registro.getId());
    }

    public void editRegistro(Registro registroNuevo, Registro registroViejo) {
           if (registros.containsKey(registroViejo.getId())) {
            registros.replace(registroViejo.getId(), registroNuevo);
        } else {
            System.out.println("Registro no encontrado.");
        }
    }

    public Registro getRegistro(int id) {
        return registros.get(id);
    }

    public Map<Integer, Registro> getRegistros() {
        return registros;
    }

    public int getSize() {
        return registros.size();
    }
}
