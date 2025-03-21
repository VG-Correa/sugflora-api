package com.spec.api_sugflora.dto;

import com.spec.api_sugflora.model.Campo;
import com.spec.api_sugflora.model.interfaces.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CampoDTO extends IntDomainDTO implements DTO<Campo> {

    private UsuarioDTO responsavel;
    private ProjetoDTO projeto;
    private String nome;
    private String descricao;
    private String endereco;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;

    public CampoDTO(Campo campo) {
        initBy(campo);
    }

    @Override
    public Class<Campo> getModelClass() {
        return Campo.class;
    }

}
