package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioDTO extends UuidDomainDTO implements DTO<Usuario>{
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private String endereco;

    UsuarioDTO(){}

    public UsuarioDTO(Usuario model) {
        this.initByModel(model);
    }

    @Override
    public Usuario toModel() {
        return new Usuario(this);
    }

    @Override
    public void initByModel(Usuario model) {
            this.nome = model.getNome();
            this.cpf = model.getCpf();
            this.rg = model.getRg();
            this.email = model.getEmail();
            this.endereco = model.getEndereco();
            this.senha = model.getSenha();
    }

    @Override
    public Class<Usuario> getModelClass() {
        return Usuario.class;
    }
}
