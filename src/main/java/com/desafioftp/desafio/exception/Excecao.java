package com.desafioftp.desafio.exception;

import com.desafioftp.desafio.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Excecao {
    @Autowired
    Repositorio repositorio;


}
