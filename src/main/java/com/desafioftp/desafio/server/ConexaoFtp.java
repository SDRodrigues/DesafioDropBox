package com.desafioftp.desafio.server;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ConexaoFtp {

    private int port = 21;
    private String host = "172.17.0.1";
    private String usuario = "rodrigues";
    private String senha = "rodrigues";

    FTPClient ftp = new FTPClient();

    public FTPClient conecta(String usuario, String senha) {
        try {
            ftp.connect(host, port);
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

