package com.spec.api_sugflora.model;

import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.model.interfaces.DTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;

public class Projeto extends IntDomain implements DTOConvertable<ProjetoDTO> {

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @Column(nullable = false, unique = false)
    private Usuario dono;

    public Projeto(){}

    public Projeto(ProjetoDTO dto) {
        this.InitByDTO(dto);
    }

    @Override
    public Class<ProjetoDTO> getDTOClass() {
       return ProjetoDTO.class;
    }

    @Override
    public void InitByDTO(ProjetoDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'InitByDTO'");
    }
    

}
