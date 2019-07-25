package com.desafioftp.desafio.server;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Conexao {

    private int porta = 21;
    private String servidor = "172.17.0.1";
    private String usuario = "rodrigues";
    private String senha = "rodrigues";


    public boolean conecta(String usuario, String senha) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(this.servidor, porta);
            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                senha = this.senha;
                usuario = this.usuario;
                ftp.login(usuario, senha);
            } else {
                disconecta();
                System.out.println("Conexão recusada");
                System.exit(1);
                return false;
            }
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return true;
    }

    public void disconecta() {
        FTPClient ftp = new FTPClient();

        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException erro) {
            erro.getMessage();
        }
    }



}

