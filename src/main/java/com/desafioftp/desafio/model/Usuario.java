package com.desafioftp.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private Integer id;
    private String name;
    private Integer idade;
    private String profissao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
