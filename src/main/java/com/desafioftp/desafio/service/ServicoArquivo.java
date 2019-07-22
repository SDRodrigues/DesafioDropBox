package com.desafioftp.desafio.service;

import com.desafioftp.desafio.server.ConexaoFtp;
import com.desafioftp.desafio.usuario.UsuarioUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ServicoArquivo {

    private ConexaoFtp conexaoFtp;

    @Autowired
    public ServicoArquivo(ConexaoFtp conexaoFtp) {
        this.conexaoFtp = conexaoFtp;
    }

    public void listarArquivos(UsuarioUpload usuario) {
        FTPClient ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
        try {
            ftpClient.listFiles();
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }

    public void enviarArquivos(MultipartFile multipartFile, UsuarioUpload usuario) {
        try {
            FTPClient ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
             ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
        catch (IOException erro) {
            erro.getMessage();
        }

    }

    public void excluirArquivos (String arquivo, UsuarioUpload usuario) {
        FTPClient ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
            try {
                ftpClient.deleteFile(arquivo);
            }
            catch (IOException erro) {
                erro.getMessage();
            }
        }



    }
