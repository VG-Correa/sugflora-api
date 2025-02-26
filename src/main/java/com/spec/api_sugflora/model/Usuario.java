package com.spec.api_sugflora.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Usuario extends UuidDomain implements DTOConvertable {
    
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

    public Usuario(){
    }

    public Usuario(DTO dto) {
        this.InitByDTO(dto);
    }

    public void CriptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);
    }

    @Override
    public UsuarioDTO toDTO() {
        return new UsuarioDTO(this);
    }

    @Override
    public boolean InitByDTO(DTO dto) {

        if (dto instanceof UsuarioDTO) {
            UsuarioDTO usuarioDTO = (UsuarioDTO) dto;
            this.nome = usuarioDTO.getNome();
            this.cpf = usuarioDTO.getCpf();
            this.rg = usuarioDTO.getRg();
            this.email = usuarioDTO.getEmail();
            this.endereco = usuarioDTO.getEndereco();
            CriptografarSenha(usuarioDTO.getSenha());
            copyDomainDTO(usuarioDTO);
            return true;
        }

        return false;
    }

}
