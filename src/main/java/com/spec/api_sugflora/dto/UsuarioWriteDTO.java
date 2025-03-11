package com.spec.api_sugflora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioWriteDTO {

    private String username;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String cpf;
    private String rg;
    private String endereco;
    private String role;
   

}
