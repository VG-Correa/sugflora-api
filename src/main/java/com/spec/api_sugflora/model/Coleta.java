package com.spec.api_sugflora.model;

import java.time.LocalDate;

import com.spec.api_sugflora.dto.ColetaDTO;
import com.spec.api_sugflora.dto.ColetaWriteDTO;
import com.spec.api_sugflora.model.interfaces.DTOConvertable;

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
public class Coleta extends IntDomain implements DTOConvertable<ColetaWriteDTO, ColetaDTO> {

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;

    @ManyToOne
    @JoinColumn(name = "campo_id", nullable = false)
    private Campo campo;

    @ManyToOne
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;

    @Column(nullable = false, unique = false)
    private LocalDate data_coleta;

    @ManyToOne
    @JoinColumn(name = "familia_id", nullable = true)
    private Familia familia;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "especie_id", nullable = true)
    private Especie especie;

    @Column(nullable = true, unique = false)
    private String observacao;

    public Coleta(ColetaWriteDTO coletaWriteDTO) {
        initBy(coletaWriteDTO);
    }

    @Override
    public Class<ColetaDTO> getDTOClass() {
        return ColetaDTO.class;
    }

    public boolean isValid() {

        if (this.getProjeto() == null) {
            throw new IllegalArgumentException("Projeto não pode ser nulo");
        }

        if (this.getProjeto().getId() == null) {
            throw new IllegalArgumentException("Projeto não pode ser nulo");
        }

        if (this.getCampo() == null) {
            throw new IllegalArgumentException("Campo não pode ser nulo");
        }
        if (this.getCampo().getId() == null) {
            throw new IllegalArgumentException("Campo não pode ser nulo");
        }
        if (this.getCampo().getId() <= 0) {
            throw new IllegalArgumentException("Campo não pode ser nulo");
        }
        if (this.getResponsavel() == null || this.getResponsavel().getId() == null) {
            throw new IllegalArgumentException("Responsável não pode ser nulo");
        }

        if (this.getFamilia() == null && this.getGenero() != null) {
            throw new IllegalArgumentException("Familia não pode ser nulo se o gênero for informado");
        }

        if (this.getGenero() == null && this.getEspecie() != null) {
            throw new IllegalArgumentException("Gênero não pode ser nulo se a espécie for informada");
        }

        return true;
    }

}
