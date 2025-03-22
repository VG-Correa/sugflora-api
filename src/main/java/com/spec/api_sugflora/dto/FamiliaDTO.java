package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Familia;
import com.spec.api_sugflora.model.interfaces.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FamiliaDTO extends IntDomainDTO implements DTO<Familia> {

    private Integer id;
    private String nome;
    private String descricao;

    @Override
    public Class<Familia> getModelClass() {
        return Familia.class;
    }

}
