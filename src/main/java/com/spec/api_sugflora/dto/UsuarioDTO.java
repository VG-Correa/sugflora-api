package com.spec.api_sugflora.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.model.Usuario;
import com.spec.speedspring.core.dto.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioDTO extends UuidDomainDTO implements DTO<Usuario> {
    private String username;
    private String nome;
    private String sobrenome;
    private String email;

    private String cpf;
    private String rg;
    private String endereco;

    UsuarioDTO() {
    }

    public UsuarioDTO(Usuario model) {
        this.initBy(model);
    }

    public Class<Usuario> getModelClass() {
        return Usuario.class;
    }
}
