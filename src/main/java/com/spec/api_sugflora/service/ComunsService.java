package com.spec.api_sugflora.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.model.Usuario;

@Service
public class ComunsService {

    @Autowired
    UsuarioService usuarioService;

    @SuppressWarnings("deprecation")
    public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean UUID_USUARIO_EXISTS(UUID uuid) {
        Usuario usuario = usuarioService.findById(uuid).get();
        return usuario != null;
    }

}
