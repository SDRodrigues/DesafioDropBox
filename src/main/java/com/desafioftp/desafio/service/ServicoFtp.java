package com.desafioftp.desafio.service;

import com.desafioftp.desafio.usuario.UsuarioUpload;
import org.springframework.stereotype.Service;

@Service
public class ServicoFtp {
     UsuarioUpload usuarioUpload;

    public ServicoFtp(UsuarioUpload usuarioUpload) {
        this.usuarioUpload = usuarioUpload;
    }


}
