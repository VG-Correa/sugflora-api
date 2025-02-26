package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioDTO extends UuidDomainDTO implements DTO{
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private String endereco;

    UsuarioDTO(){}

    public UsuarioDTO(DTOConvertable model) {
        this.initByModel(model);
    }

    @Override
    public Usuario toModel() {
        return new Usuario(this);
    }

    @Override
    public boolean initByModel(DTOConvertable model) {
        if (model instanceof Usuario) {
            Usuario usuario = (Usuario) model;
            this.nome = usuario.getNome();
            this.cpf = usuario.getCpf();
            this.rg = usuario.getRg();
            this.email = usuario.getEmail();
            this.endereco = usuario.getEndereco();
            this.senha = usuario.getSenha();
            // TODO: Parei aqui
            return true;
        }

        return false;
    }
}
