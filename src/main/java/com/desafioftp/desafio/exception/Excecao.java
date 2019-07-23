package com.desafioftp.desafio.exception;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Excecao extends RuntimeException {
    public Excecao(String message) {
        super(message);
    }

}
