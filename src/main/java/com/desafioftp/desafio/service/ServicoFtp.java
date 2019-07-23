package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.ConexaoFtp;
import com.desafioftp.desafio.model.UsuarioUpload;
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

    public void enviarArquivos(MultipartFile multipartFile, Usuario usuario) {
        try {
            ftpClient = conexaoFtp.conecta(usuario.getNome(), usuario.getSenha());
            ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
        catch (IOException erro) {
            erro.getMessage();
        }


    }

    public boolean listarArquivos() {
        UsuarioUpload usuario = new UsuarioUpload();
        try {
            ftpClient = conexaoFtp.conecta(usuario.upload().getNome(), usuario.upload().getSenha());
            ftpClient.listFiles();
        }
        catch (IOException erro) {
            erro.getMessage();

        }
        return true;
    }

    public void excluirArquivos (String arquivo, UsuarioUpload usuario) {
         ftpClient = conexaoFtp.conecta(usuario.upload().getNome(), usuario.upload().getSenha());
            try {
                ftpClient.deleteFile(arquivo);
            }
            catch (IOException erro) {
                erro.getMessage();
            }
        }

    }
