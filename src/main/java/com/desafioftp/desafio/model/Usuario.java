package com.desafioftp.desafio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Usuario {
    @Id
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;
    private List<Arquivos> arquivos;
}
