package com.spec.api_sugflora.dto;

import java.time.LocalDate;

import com.spec.api_sugflora.model.Coleta;
import com.spec.api_sugflora.model.interfaces.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ColetaDTO extends IntDomainDTO implements DTO<Coleta> {

    private ProjetoDTO projeto;
    private CampoDTO campo;
    private UsuarioDTO responsavel;
    private LocalDate data_coleta;
    private FamiliaDTO familia;
    private GeneroDTO genero;
    private EspecieDTO especie;
    private String coletor;

    @Override
    public Class<Coleta> getModelClass() {
        return Coleta.class;
    }

}
