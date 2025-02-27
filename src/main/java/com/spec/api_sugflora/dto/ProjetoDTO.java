package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProjetoDTO extends IntDomainDTO implements DTO<Projeto> {
    private String nome;
    private String descricao;
    private Usuario dono;

    public ProjetoDTO(Projeto projeto) {
        this.initByModel(projeto);
    }

    @Override
    public Projeto toModel() {
        return new Projeto(this);
    }

    @Override
    public void initByModel(Projeto model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initByModel'");
    }
}
