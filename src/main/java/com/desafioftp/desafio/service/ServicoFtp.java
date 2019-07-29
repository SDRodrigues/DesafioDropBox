package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ServicoFtp {
        private Conexao conexao;
        private ServicoUsuario servicoUsuario;

        @Autowired
    public ServicoFtp(Conexao conexao, ServicoUsuario servicoUsuario) {
        this.conexao = conexao;
        this.servicoUsuario = servicoUsuario;
    }

    public boolean enviaArquivo(MultipartFile arquivo, Integer id) {
        FileInputStream arqEnviar;
        FTPClient ftpClient = new FTPClient();
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        try {
            File confFile = new File(arquivo.getOriginalFilename());
            confFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(confFile);
            fos.write(arquivo.getBytes());
            fos.close();
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.enterLocalPassiveMode();
            arqEnviar = new FileInputStream(String.valueOf(arquivo));
            if (ftpClient.storeFile(String.valueOf(arquivo), arqEnviar)) {
                return true;
            }
            arqEnviar.close();
        }
            catch(IOException erro) {
            erro.getMessage();
        }
            conexao.disconecta();
        return false;
    }

    public ArrayList<Arquivos> buscaArquivos(Optional<Usuario> usuario) {
        FTPClient ftpClient = new FTPClient();
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.enterLocalPassiveMode();
            FTPFile[] arquivosBuscados = ftpClient.listFiles();
            ArrayList<Arquivos> listaArquivos = new ArrayList<>();
            for (FTPFile ftpFile : arquivosBuscados) {
                Arquivos arquivos = new Arquivos(ftpFile);
                listaArquivos.add(arquivos);
            }
             return listaArquivos;
        } catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }

    public void excluirArquivos(Optional<Usuario> usuario, String nomeArquivo) {
        FTPClient ftpClient = new FTPClient();
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.deleteFile(String.valueOf(nomeArquivo));
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }

    public String[] nomeDiretorio(String diretorio, Usuario usuario) {
        FTPClient ftpClient = new FTPClient();
        String[] nomeDir = null;
            try {
                conexao.conecta(usuario.getNome(), usuario.getSenha());
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(diretorio);
                nomeDir = ftpClient.listNames();
            } catch (IOException erro) {
                erro.getMessage();
            }finally {
            conexao.disconecta();
            }
            return nomeDir;
        }

    }
