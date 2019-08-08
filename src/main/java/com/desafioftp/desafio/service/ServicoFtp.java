package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        private int porta = 21;
        private String server = "127.0.0.1";
        private String servidor = "servidor-ftp";


    private FTPClient conecta() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(this.server, this.porta);
            System.out.println(ftpClient.getReplyString());
            ftpClient.login("rodrigues", "rodrigues");
            System.out.println(ftpClient.getReplyString());

        }
        catch (IOException erro) {
            erro.getMessage();
        } finally {
            disconecta();
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

//    public void uploadArquivo(String id, MultipartFile arquivo) {
//        FileInputStream arqEnviar;
//        ftpClient = conexao.conecta();
//        ftpClient.enterLocalPassiveMode();
//        criarDiretorio(id);
//        File confFile = new File(arquivo.getOriginalFilename());
//        try {
//            confFile.createNewFile();
//            FileInputStream fos = new FileInputStream(confFile);
//            fos.close();
//            arqEnviar = new FileInputStream(String.valueOf(arquivo));
//            arqEnviar.close();
//        }
//            catch(IOException erro) {
//            erro.getMessage();
//        } finally {
//            conexao.disconecta();
//        }
//
//    }

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










    public void excluirArquivos(String id, String nomeArquivo) {
        ftpClient = conecta();
          criarDiretorio(id);
        try {
            ftpClient.deleteFile(nomeArquivo);
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

    public List<String> getFilesName(String id) {
        try {
            criarDiretorio(id);
            List files = Arrays.asList(ftpClient.listNames());
            disconecta();
            return files;
        } catch (IOException erro) {
            erro.getMessage();
        }
        return new ArrayList<>();
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


    public Page<Arquivos> buscaArquivosPaginados(Optional<Usuario> usuario, Integer quantidade, Integer pagina) {
        ftpClient = conecta();
        return arquivosPaginados(buscaArquivos(usuario), pagina, quantidade);
    }

    private static Page<Arquivos> arquivosPaginados(ArrayList<Arquivos> arquivos, int pagina, int quantidade) {
        List<Arquivos> listaArquivos = new ArrayList<>(arquivos);
        int limiteArquivos = Math.min((quantidade * pagina), listaArquivos.size());
        return new PageImpl<>(listaArquivos.subList((pagina - 1) * quantidade, limiteArquivos),
                PageRequest.of(pagina, quantidade), limiteArquivos);
    }
}
