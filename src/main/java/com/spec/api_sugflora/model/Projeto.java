package com.spec.api_sugflora.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.dto.ProjetoDTO;
import com.spec.api_sugflora.dto.ProjetoWriteDTO;
import com.spec.speedspring.core.dtoConvertable.DTOConvertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Projeto extends IntDomain implements DTOConvertable<ProjetoWriteDTO, ProjetoDTO> {

    @Column(nullable = false, unique = false)
    private String nome;

    @Column(nullable = true, unique = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_dono_id", nullable = false, unique = false)
    private Usuario dono;

    @Column(nullable = false, unique = false)
    private boolean isPublic = false;

    @Column(nullable = true, unique = false)
    private byte[] imagem;

    @Column(nullable = false, unique = false)
    private LocalDateTime inicio;
    
    @Column(nullable = false, unique = false)
    private LocalDateTime termino;

    public Projeto(ProjetoWriteDTO dto) {
        this.initBy(dto);
    }

    @Override
    @JsonIgnore
    public Class<ProjetoDTO> getDTOClass() {
        return ProjetoDTO.class;
    }

    @Override
    public boolean getLog() {
        return false;
    }

}
