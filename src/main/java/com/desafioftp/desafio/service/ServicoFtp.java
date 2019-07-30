package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        private FTPClient ftpClient = new FTPClient();


    @Autowired
    public ServicoFtp(Conexao conexao, ServicoUsuario servicoUsuario) {
        this.conexao = conexao;
        this.servicoUsuario = servicoUsuario;
    }

    public ServicoFtp(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public boolean enviaArquivo(MultipartFile arquivo, Integer id) {
        FileInputStream arqEnviar;
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        try {
            File confFile = new File(arquivo.getOriginalFilename());
            confFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(confFile);
            fos.write(arquivo.getBytes());
            fos.close();
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
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
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
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

    public FTPFile[] buscaArquivosDoUsuario(Optional<Usuario> usuario) {
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.enterLocalPassiveMode();
            return ftpClient.listFiles();
        } catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }

    public void excluirArquivos(Optional<Usuario> usuario, String nomeArquivo) {
        try {
            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
            ftpClient.deleteFile(nomeArquivo);
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }

    public Page<Arquivos> buscaArquivosPaginados(Optional<Usuario> usuario, Integer paginas, Integer quantidade) {
        conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
        return null;
    }

    public String[] mudaDiretorio(String diretorio, Usuario usuario) {
        String[] nomeDir = null;
            try {
                conexao.conecta(usuario.getNome(), usuario.getSenha());
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
