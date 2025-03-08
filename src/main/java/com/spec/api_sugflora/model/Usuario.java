package com.spec.api_sugflora.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Usuario extends UuidDomain implements DTOConvertable<UsuarioDTO> {
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = false, unique = false)
    private String sobrenome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String rg;

    @Column(nullable = true, unique = false)
    private String endereco;

    @Column(nullable = false, unique = false)
    private String role;

    public Usuario(){
    }

    public Usuario(UsuarioDTO dto) {
        this.initBy(dto);
    }

    public void CriptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }

    @Override
    @JsonIgnore
    public Class<UsuarioDTO> getDTOClass() {
        return UsuarioDTO.class;
    }

}
