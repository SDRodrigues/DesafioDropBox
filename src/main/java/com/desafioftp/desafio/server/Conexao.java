package com.desafioftp.desafio.server;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Conexao {

    private int porta = 21;
    private String servidor = "172.17.0.1";
    private String server = "127.0.0.1";
    private String usuario = "rodrigues";
    private String senha = "rodrigues";
    private FTPClient ftp = new FTPClient();



    public FTPClient conecta(String usuario, String senha) {
        try {
            ftp.connect(this.server, porta);
            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                senha = this.senha;
                usuario = this.usuario;
                ftp.login(usuario, senha);
                ftp.enterLocalPassiveMode();
                disconecta();
            } else {
                disconecta();
                System.out.println("Conexão recusada");
                System.exit(1);
            }
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return ftp;
    }

    public void disconecta() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException erro) {
            erro.getMessage();
        }
    }


    public FTPFile[] buscaArquivosDoUsuario() {
        try {
            return ftp.listFiles();
        } catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }


}

