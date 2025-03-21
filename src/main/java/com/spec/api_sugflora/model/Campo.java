package com.spec.api_sugflora.model;

import com.spec.api_sugflora.dto.CampoDTO;
import com.spec.api_sugflora.dto.CampoWriteDTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Campo extends IntDomain implements DTOConvertable<CampoWriteDTO, CampoDTO> {

    @Column(nullable = false, unique = false)
    private Usuario responsavel;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = false, unique = false)
    private String descricao;

    @Column(nullable = false, unique = false)
    private String endereco;
    @Column(nullable = false, unique = false)
    private String cidade;
    @Column(nullable = false, unique = false)
    private String estado;
    @Column(nullable = false, unique = false)
    private String pais;
    @Column(nullable = false, unique = false)
    private String cep;

    public Campo(CampoWriteDTO campoWriteDTO) {
        initBy(campoWriteDTO);
    }

    @Override
    public Class<CampoDTO> getDTOClass() {
        return CampoDTO.class;
    }

}
