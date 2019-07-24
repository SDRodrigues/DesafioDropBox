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

    FTPClient ftp = new FTPClient();

    public boolean conecta() {
        try {
            ftp.connect(this.servidor, porta);
            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.login(this.usuario, this.senha);
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
        try {
            this.ftp.logout();
            this.ftp.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

