package com.desafioftp.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Arquivos {
    private Integer id;
    private List<String> listaDeArquivos;
}
