package com.desafioftp.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private Integer id;
    private String name;
    private Integer idade;
    private String profissao;
}
