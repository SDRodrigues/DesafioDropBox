package com.desafioftp.desafio.model;

import com.desafioftp.desafio.repository.Repositorio;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Usuario {
    @Id
    private String id;
    private String nome;
    private Integer idade;
    private String profissao;
}
