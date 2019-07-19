package com.desafioftp.desafio.ftp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;


@AllArgsConstructor
@NoArgsConstructor
public class Conexao {

    private String host = "172.17.0.1";
    private String user = "rodrigues";
    private String senha = "rodrigues";
    private int port = 21;

    private FTPClient ftp;

    void conecta() {
        ftp = new FTPClient();

        try {
            ftp.connect(host, port);
        }
        catch (IOException erro) {
            System.out.println(erro);
        }
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            try {
                ftp.disconnect();
            }
            catch (IOException erro) {
                System.out.println(erro + "Nao conseguiu se conectar ao servidor FTP");
            }
        } try {
            ftp.login(user, senha);
        }
        catch (IOException erro) {
            System.out.println(erro);
        }
    }

    void desconecta() {
        try {
            ftp.disconnect();
        }
        catch (IOException erro) {
            System.out.println(erro);
        }
    }
}

