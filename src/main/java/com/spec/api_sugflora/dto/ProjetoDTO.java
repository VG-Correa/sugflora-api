package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Projeto;
import com.spec.api_sugflora.model.Usuario;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO extends IntDomainDTO implements DTO<Projeto> {
    private String nome;
    private String descricao;
    private UsuarioDTO dono;

    public ProjetoDTO(Projeto projeto) {
        this.initBy(projeto);
    }

    @Override
    public Class<Projeto> getModelClass() {
            return Projeto.class;
    }
}
