package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Genero;
import com.spec.speedspring.core.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GeneroDTO extends IntDomainDTO implements DTO<Genero> {

    private Integer id;
    private String nome;
    private String descricao;
    private FamiliaDTO familia;

    @Override
    public Class<Genero> getModelClass() {
        return Genero.class;
    }

}
