package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
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

    public FTPFile[] buscaArquivos(String diretorio, Integer id) {
        FTPFile[] arquivosConfig = null;
        FTPClient ftpClient = new FTPClient();
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(diretorio);
            arquivosConfig = ftpClient.listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            conexao.disconecta();
        }
        return arquivosConfig;
    }

    public void excluirArquivos(String arquivo, Usuario usuario) {
        FTPClient ftpClient = new FTPClient();
        try {
            conexao.conecta(usuario.getNome(), usuario.getSenha());
            ftpClient.deleteFile(arquivo);
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
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
            conexao.disconecta();
            }
            return nomeDir;
        }

    }
