package com.spec.api_sugflora.model;

import java.time.LocalDateTime;
import java.util.Base64;

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

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_dono_id", nullable = false)
    private Usuario dono;

    @Column(nullable = false)
    private boolean isPublic = false;

    @Column(nullable = true)
    private byte[] imagem;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = true)
    private LocalDateTime termino;

    @Column(nullable = true)
    private String responsavel;

    // Construtor que inicializa a partir do DTO
    public Projeto(ProjetoWriteDTO dto) {
        this.initBy(dto);
        setImagemBase64(dto.getImagemBase64());
    }

    // Método para atualizar os campos com dados do DTO
    public void initBy(ProjetoWriteDTO dto) {
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.isPublic = dto.isPublic();
        this.inicio = dto.getInicio();
        this.termino = dto.getTermino();
        this.responsavel = dto.getResponsavel();
        // Nota: dono deve ser setado separadamente (pois é entidade)
    }

    // Atualiza imagem convertendo Base64 para byte[]
    public void setImagemBase64(String imagemBase64) {
        if (imagemBase64 != null && !imagemBase64.isEmpty()) {
            this.imagem = Base64.getDecoder().decode(imagemBase64);
        }
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
