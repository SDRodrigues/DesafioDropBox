package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ServicoFtp {
        private Conexao conexao;
        private FTPClient ftpClient;


    @Autowired
    public ServicoFtp(Conexao conexao) {
        this.conexao = conexao;
    }

    public ServicoFtp(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

//    public boolean enviaArquivo(Integer id, MultipartFile arquivo) {
//        FileInputStream arqEnviar;
//        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
//        try {
//            File confFile = new File(arquivo.getOriginalFilename());
//            confFile.createNewFile();
//            FileOutputStream fos = new FileOutputStream(confFile);
//            fos.write(arquivo.getBytes());
//            fos.close();
//            conexao.conecta(usuario.get().getNome(), usuario.get().getSenha());
//            arqEnviar = new FileInputStream(String.valueOf(arquivo));
//            if (ftpClient.storeFile(String.valueOf(arquivo), arqEnviar)) {
//                return true;
//            }
//            arqEnviar.close();
//        }
//            catch(IOException erro) {
//            erro.getMessage();
//        } finally {
//            conexao.disconecta();
//
//            return false;
//        }
//
//    }
    private void mudaDiretorio(String id) {
        try {
            ftpClient.makeDirectory(id);
            ftpClient.changeWorkingDirectory("/" + id);
        } catch (IOException erro) {
            erro.getMessage();
        }
    }

    public void storeFile(String id, MultipartFile file) {
        ftpClient = conexao.conecta();
           mudaDiretorio(id);
        try {
            ftpClient.storeFile(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException erro) {
            erro.getMessage();
        }
    }

    public void excluirArquivos(String id, String nomeArquivo) {
        ftpClient = conexao.conecta();
          mudaDiretorio(id);
        try {
            ftpClient.deleteFile(nomeArquivo);
        }
        catch (IOException erro) {
            erro.getMessage();
        }
    }

    public FTPFile[] buscaArquivosDoUsuario(Optional<Usuario> usuario) {
        try {
            FTPClient con = conexao.conecta();
            return con.listFiles();
        }
        catch (IOException erro) {
            erro.getMessage();
            return null;
        }
    }


    public ArrayList<Arquivos> buscaArquivos(Optional<Usuario> usuario) {
        try {
            conexao.conecta();
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


    public Page<Arquivos> buscaArquivosPaginados(Optional<Usuario> usuario, PageRequest pageRequest) {
        return null;
    }
}
