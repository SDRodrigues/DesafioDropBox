package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import com.desafioftp.desafio.model.UsuarioDto;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@NoArgsConstructor
public class ServicoUpload {

    private Conexao conexao;
    private FTPClient ftpClient;

    @Autowired
    public ServicoUpload(Conexao conexao) {
        this.conexao = conexao;
    }

    public ServicoUpload(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public boolean enviarArquivos(MultipartFile multipartFile, Usuario usuario) {
        try {
            this.ftpClient = conexao.conecta(usuario.getNome(), usuario.getSenha());
            return this.ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        }
        catch (IOException erro) {
            erro.getMessage();
            return false;
        }
    }



    public boolean listarArquivos() {
        try {
            ftpClient = conexao.conecta("rodrigues", "rodrigues");
            ftpClient.listFiles();
        }
        catch (IOException erro) {
            erro.getMessage();

        }
        return true;
    }



    public void excluirArquivos (String arquivo, UsuarioDto usuario) {
            try {
                ftpClient = conexao.conecta("rodrigues", "rodrigues");
                ftpClient.deleteFile(arquivo);
            }
            catch (IOException erro) {
                erro.getMessage();
            }
        }

    }
