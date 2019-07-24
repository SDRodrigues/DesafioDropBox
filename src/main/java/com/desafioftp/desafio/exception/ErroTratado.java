package com.desafioftp.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErroTratado {

    @ExceptionHandler(ObjetoNaoEncontrado.class)

    public ResponseEntity<ErroPadrao> naoEncontrou(ObjetoNaoEncontrado objetoNaoEncontrado,
                                                   HttpServletRequest httpServletRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadrao erroPadrao = new ErroPadrao(
                System.currentTimeMillis(),
                status.value(),
                "Usuário não encontrado",
                objetoNaoEncontrado.getMessage(),
                httpServletRequest.getRequestURI());
        return ResponseEntity.status(status).body(erroPadrao);
    }
}
