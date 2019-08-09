package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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


    public FTPFile[] listaUpload(Optional<Usuario> usuario) {
        ftpClient = conecta();
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles();
            criarDiretorio(usuario.get().getId());
            return files;
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return files;
    }


    public Page<FTPFile> buscaArquivosPaginados(Optional<Usuario> usuario, Integer paginas, Integer filtro) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        criarDiretorio(usuario.get().getId());
        try {
            return getPaginacao(ftpClient.listFiles(),paginas,filtro);
        } catch (IOException erro) {
            erro.getMessage();
        }
        return null;
    }

    private Page<FTPFile> getPaginacao(FTPFile[] listFiles, Integer paginas, Integer filtro) {
        PageRequest pageRequest = new PageRequest(paginas,filtro);
        List<FTPFile> lista = new ArrayList<>(Arrays.asList(listFiles));
        int max = Math.min(filtro * (paginas + 1), lista.size());
        Page<FTPFile> ftpFilePage ;
        ftpFilePage= new PageImpl<>(lista.subList(paginas*filtro,max), pageRequest,lista.size());
        return  ftpFilePage;
    }

    public void downloadArquivo(String arquivo, Optional<Usuario> usuario) {
        String usuarioId = usuario.get().getId();
        criarDiretorio(usuarioId);
        ftpClient = conecta();
        try {
            try (FileOutputStream fileOutputStream = new FileOutputStream("arquivos" + arquivo)) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.retrieveFile(arquivo, fileOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

//    public ArrayList<Arquivos> buscaArquivos(Optional<Usuario> usuario) {
//        ftpClient = new FTPClient();
//        ftpClient = conecta();
//        criarDiretorio(usuario.get().getId());
//        try {
//            FTPFile[] files = ftpClient.listFiles();
//            ArrayList<Arquivos> listaArquivos = new ArrayList<>();
//            for (FTPFile file : files) {
//                Arquivos arquivos = new Arquivos(file);
//                listaArquivos.add(arquivos);
//            }
//            return listaArquivos;
//        } catch (IOException erro) {
//            erro.getMessage();
//            return null;
//        }
//    }

}
