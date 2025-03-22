package com.spec.api_sugflora.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.UsuarioDTO;
import com.spec.api_sugflora.dto.UsuarioWriteDTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Usuario extends UuidDomain implements DTOConvertable<UsuarioWriteDTO, UsuarioDTO>, UserDetails {

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

    public Usuario() {
    }

    public Usuario(UsuarioWriteDTO dto) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
