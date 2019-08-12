package com.desafioftp.desafio.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ObjetoNaoEncontrado extends RuntimeException {
    public ObjetoNaoEncontrado(String message) {
        super(message);
    }

}
