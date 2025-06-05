package com.spec.api_sugflora.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.service.UsuarioService;

@Service
public class InitUsers {

    private final UsuarioService usuarioService;

    public InitUsers(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void run(PasswordEncoder passwordEncoder) throws Exception {

        List<UsuarioWriteDTO> usuarios = new ArrayList<>();

        UsuarioWriteDTO adm = new UsuarioWriteDTO();
        adm.setUsername("ADM");
        adm.setEmail("adm@adm.com");
        adm.setCpf("000.000.000-00");
        adm.setRg("00.000.000-0");
        adm.setEndereco(null);
        adm.setSenha("adm");
        adm.setNome("adm");
        adm.setSobrenome("adm");
        adm.setRole("ROLE_ADM");

        usuarios.add(adm);

        Save(usuarios, passwordEncoder);

    }

    private void Save(List<UsuarioWriteDTO> usuarios, PasswordEncoder passwordEncoder) {
        usuarios.stream().forEach(usuario -> {

            try {
                Usuario usuarioModel = new Usuario(usuario);
                Usuario usuarioSaved = usuarioService.save(usuarioModel, passwordEncoder);

                if (usuarioSaved != null) {
                    System.err.println("Usuário" + usuario.getUsername() + " cadastrado com sucesso");
                } else {
                    System.err.println("Erro ao cadastrar usuario: " + usuario.getUsername());
                }

            } catch (Exception e) {
                System.err.println("Erro ao cadastrar usuario");
                System.err.println(e.getMessage());
            }

        });
    }

}
