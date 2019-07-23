package com.desafioftp.desafio.service;

import com.desafioftp.desafio.server.ConexaoFtp;
import com.desafioftp.desafio.usuario.UsuarioUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ServicoFtp {

    private ConexaoFtp conexaoFtp;
    private FTPClient ftpClient;


    @Autowired
    public ServicoFtp(ConexaoFtp conexaoFtp) {
        this.conexaoFtp = conexaoFtp;
    }

    public ServicoFtp(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public boolean enviarArquivos(MultipartFile multipartFile, UsuarioUpload usuario) {
        try {
            ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
            ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return false;

    }

    public boolean listarArquivos(UsuarioUpload usuario) {
         ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
        try {
            ftpClient.listFiles();
        }
        catch (IOException erro) {
            erro.getMessage();

        }
        return false;
    }

    public void excluirArquivos (String arquivo, UsuarioUpload usuario) {
         ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
            try {
                ftpClient.deleteFile(arquivo);
            }
            catch (IOException erro) {
                erro.getMessage();
            }
        }

    }
