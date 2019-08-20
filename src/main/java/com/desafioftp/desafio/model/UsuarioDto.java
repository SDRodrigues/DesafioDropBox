package com.desafioftp.desafio.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String id;
    private String nome;
    private Integer idade;
    private String profissao;

}
