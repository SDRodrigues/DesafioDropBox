package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        private static final Logger logger = LoggerFactory.getLogger(ServicoFtp.class);


    private FTPClient conecta() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect("127.0.0.1", 21);
            ftpClient.login("rodrigues", "rodrigues");
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

    private FTPClient criarDiretorio(String id) {
        try {
            if (!Arrays.asList(ftpClient.listDirectories()).contains(id)) {
                ftpClient.makeDirectory(id);
            }
            ftpClient.changeWorkingDirectory("/" + id);
        } catch (IOException erro) {
            erro.getMessage();
        }
        return ftpClient;
    }

    private FTPClient verificaDiretorio(String id, FTPClient ftpClient) {
        boolean direorioExiste= false;
        try {
            FTPFile[] listaDiretorios = ftpClient.listDirectories();
            for(FTPFile f:listaDiretorios){
                if(f.getName().equals(id)){
                    direorioExiste=true;
                }
            }
            if(!direorioExiste) {
                if (ftpClient.makeDirectory(id)) {
                    logger.info("Diretorio criado");
                } else logger.info("Diretorio nao criado");
            }
            return criarDiretorio(id);
        } catch (IOException e) {
            e.getMessage();
        }
        return ftpClient;
    }


    public void salvaArquivo(String id, MultipartFile file) {
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


    public FTPFile[] listaTodosArquivos(String id) {
        ftpClient = conecta();
        ftpClient.enterLocalPassiveMode();
        ftpClient = verificaDiretorio(id, ftpClient);
        FTPFile[] files = new FTPFile[0];
        try {
            files = ftpClient.listFiles();
            criarDiretorio(id);
            return files;
        }
        catch (IOException erro) {
            erro.getMessage();
        }
        return files;
    }


    public Page<FTPFile> listaArquivosPaginados(String id, Integer paginas, Integer filtro) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        ftpClient.enterLocalPassiveMode();
        ftpClient = verificaDiretorio(id, ftpClient);
        criarDiretorio(id);
        try {
            return criouArquivosPaginados(ftpClient.listFiles(),paginas,filtro);
        } catch (IOException erro) {
            erro.getMessage();
        }
        return null;
    }



    private Page<FTPFile> criouArquivosPaginados(FTPFile[] listFiles, Integer paginas, Integer filtro) {
        PageRequest pageRequest = new PageRequest(paginas,filtro);
        List<FTPFile> lista = new ArrayList<>(Arrays.asList(listFiles));
        int max = Math.min(filtro * (paginas + 1), lista.size());
        Page<FTPFile> ftpFilePage ;
        ftpFilePage= new PageImpl<>(lista.subList(paginas*filtro,max), pageRequest,lista.size());
        return  ftpFilePage;
    }



    public void downloadArquivo(String arquivo, String id) {
        criarDiretorio(id);
        ftpClient = conecta();
        try {
            try (FileOutputStream fileOutputStream =
                         new FileOutputStream("/home/rodrigues/Documents/DesafioDropbox/arquivos/" + arquivo)) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.retrieveFile(arquivo, fileOutputStream);
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }




    public void excluirArquivos(Optional<Usuario> usuario, String nomeArquivo) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        String recebeId = usuario.get().getId();
        try {
            ftpClient.deleteFile("/" + recebeId + "/" + nomeArquivo);
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
