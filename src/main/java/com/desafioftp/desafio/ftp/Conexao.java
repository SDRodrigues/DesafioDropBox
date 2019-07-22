package com.desafioftp.desafio.ftp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Conexao {
    FTPClient ftp = new FTPClient();

    public FTPClient conecta(String usuario, String senha) {
        try {
            ftp.connect("172.17.0.1", 21);
            ftp.login(usuario, senha);
        } catch (IOException erro) {
            erro.getMessage();
        }
        return ftp;
    }

    void desconecta() {
        try {
            ftp.disconnect();
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }
}

