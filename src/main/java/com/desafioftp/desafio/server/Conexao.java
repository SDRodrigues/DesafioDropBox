package com.desafioftp.desafio.server;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class Conexao {

    private int porta = 21;
    private String server = "127.0.0.1";
    private String servidor = "servidor-ftp";
    private String usuario = "rodrigues";
    private String senha = "rodrigues";
    private FTPClient ftp;



//    public FTPClient conecta() {
//        ftp = new FTPClient();
//        try {
//                ftp.connect(this.server, this.porta);
//            System.out.println(ftp.getReplyString());
//                ftp.login(this.usuario, this.senha);
//            System.out.println(ftp.getReplyString());
//
//        }
//        catch (IOException erro) {
//            erro.getMessage();
//        }
//        return this.ftp;
//    }

    public void disconecta() {
        try {
            ftp.logout();
            ftp.disconnect();
        } catch (IOException erro) {
            erro.getMessage();
        }
    }

}

