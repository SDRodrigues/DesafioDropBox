package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ServicoFtp {
        private FTPClient ftpClient;
        private String server = "127.0.0.1";
        private String nome = "rodrigues";
        private String senha = "rodrigues";
        private int porta = 21;


    private FTPClient conecta() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(this.server, this.porta);
            ftpClient.login(this.nome, this.senha);
            System.out.println(ftpClient.getReplyString());
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return this.ftpClient;
    }

    private void disconecta() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException erro) {
            erro.getMessage();
        }
    }

    private void criarDiretorio(String id) {
        try {
            if (!Arrays.asList(ftpClient.listDirectories()).contains(id)) {
                ftpClient.makeDirectory(id);
            }
            ftpClient.changeWorkingDirectory("/" + id);
        } catch (IOException erro) {
            erro.getMessage();
        }
    }


    public void storeFile(String id, MultipartFile file) {
        ftpClient = conecta();
        ftpClient.enterLocalPassiveMode();
        criarDiretorio(id);
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(file.getOriginalFilename(), file.getInputStream());
            disconecta();
        } catch (IOException erro) {
            erro.getMessage();
        }
    }

    public ArrayList<Arquivos> buscaArquivos(Optional<Usuario> usuario) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        criarDiretorio(usuario.get().getId());
        try {
            FTPFile[] files = ftpClient.listFiles();
            ArrayList<Arquivos> listaArquivos = new ArrayList<>();
            for (FTPFile file : files) {
                Arquivos arquivos = new Arquivos(file);
                listaArquivos.add(arquivos);
            }
            return listaArquivos;
        } catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }


    public void buscaArquivosPaginados(Optional<Usuario> usuario, Pageable pageable) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        criarDiretorio(usuario.get().getId());
        try {
            ftpClient.listFiles(String.valueOf(pageable));
        } catch (IOException erro) {
            erro.getMessage();
        }
    }










    public FTPFile[] listaUpload(Optional<Usuario> usuario) {
        ftpClient = conecta();
        try {
            ftpClient.changeWorkingDirectory(usuario.get().getId());
            FTPFile[] ftpFiles = ftpClient.listFiles();
            return ftpFiles;
        }
        catch (IOException erro) {
            erro.getMessage();
            return null;
        } finally {
            disconecta();
        }
    }

    public void excluirArquivos(Optional<Usuario> usuario, String nomeArquivo) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        criarDiretorio(usuario.get().getId());
        try {
            ftpClient.deleteFile("/" + usuario.get().getId() + "/" + nomeArquivo);
            disconecta();
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }

    public void excluiDiretorio(String id) {
        ftpClient = conecta();
        try {
            ftpClient.removeDirectory("/" + id);
        } catch (IOException erro) {
            erro.getMessage();
        }
    }


}
