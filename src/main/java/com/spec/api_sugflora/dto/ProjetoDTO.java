package com.spec.api_sugflora.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spec.api_sugflora.model.Projeto;
import com.spec.speedspring.core.dto.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO extends IntDomainDTO implements DTO<Projeto> {

    private String nome;
    private String descricao;

    @JsonIgnore
    private UsuarioDTO dono;

    private boolean isPublic;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private String responsavel;

    @Schema(description = "Imagem do projeto em base64 ou URL", example = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUg...")
    private String imagemBase64;

    public ProjetoDTO(Projeto projeto) {
        this.initBy(projeto);

        if (projeto.getImagem() != null && projeto.getImagem().length > 0) {
            String base64 = java.util.Base64.getEncoder().encodeToString(projeto.getImagem());
            // Você pode ajustar o tipo da imagem, ex: "jpeg", "png", etc.
            this.imagemBase64 = "data:image/jpeg;base64," + base64;
        }
    }

    @Override
    public Class<Projeto> getModelClass() {
        return Projeto.class;
    }
}
