package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ServicoFtp {
        private FTPClient ftpClient;
        private static final String HOST = "127.0.0.1";
        private static final Integer PORTA = 21;
        private static final String USUARIO = "rodrigues";
        private static final String SENHA = "rodrigues";
        ServicoUsuario servicoUsuario;


    @Autowired
    public ServicoFtp(ServicoUsuario servicoUsuario) {
        this.servicoUsuario = servicoUsuario;
    }

    private FTPClient conecta() {
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(HOST, PORTA);
            ftpClient.login(USUARIO, SENHA);
        }
        catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
        return this.ftpClient;
    }

    private void disconecta() {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
    }

    private FTPClient criarDiretorio(String id) {
        try {
            if (!Arrays.asList(ftpClient.listDirectories()).contains(id)) {
                ftpClient.makeDirectory(id);
            }
//            ftpClient.changeWorkingDirectory("/" + id);
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
        return ftpClient;
    }

    private FTPClient verificaDiretorio(String id, FTPClient ftpClient) {
        boolean direorioExiste= false;
        try {
            FTPFile[] listaDiretorios = ftpClient.listDirectories();
            for(FTPFile files:listaDiretorios){
                if(files.getName().equals(id)){
                    direorioExiste=true;
                }
            }
            if(!direorioExiste) {
                if (ftpClient.makeDirectory(id)) {
                    log.info("Diretorio criado");
                } else log.info("Diretorio nao criado");
            }
            return criarDiretorio(id);
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
        return ftpClient;
    }

    private Page<FTPFile> criouArquivosPaginados(FTPFile[] arquivos, Integer paginas, Integer filtro) {
        PageRequest pageRequest = new PageRequest(paginas,filtro);
        List<FTPFile> lista = new ArrayList<>(Arrays.asList(arquivos));
        int max = Math.min(filtro * (paginas + 1), lista.size());
        Page<FTPFile> arquivoPaginado ;
        arquivoPaginado= new PageImpl<>(lista.subList(paginas*filtro,max), pageRequest,lista.size());
        return  arquivoPaginado;
    }

    private void downloadArquivo(String arquivo, String id) {
        criarDiretorio(id);
        ftpClient = conecta();
        try {
            try (FileOutputStream fileOutputStream =
                         new FileOutputStream("/home/rodrigues/Documents/DesafioDropbox/arquivos/" + arquivo)) {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.retrieveFile(arquivo, fileOutputStream);
            }
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
    }


    public void salvaArquivo(String id, MultipartFile file) {
        ftpClient = conecta();
        ftpClient.enterLocalPassiveMode();
        criarDiretorio(id);
        try {
            ftpClient.changeWorkingDirectory("/" + id);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(file.getOriginalFilename(), file.getInputStream());
            disconecta();
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
    }


    public Page<FTPFile> listaArquivosPaginados(String id, Integer paginas, Integer filtro) {
        ftpClient = new FTPClient();
        ftpClient = conecta();
        ftpClient.enterLocalPassiveMode();
        ftpClient = verificaDiretorio(id, ftpClient);
//        criarDiretorio(id);
        try {
            ftpClient.changeWorkingDirectory("/" + id);
            return criouArquivosPaginados(ftpClient.listFiles(),paginas,filtro);
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
        }
        return null;
    }

    public void arquivosCompartilhados(String idUsuario, String idOutroUsuario, String arquivo) {
        ftpClient = new FTPClient();
        downloadArquivo(arquivo,idUsuario);
        InputStream inputStream = IOUtils.toInputStream(arquivo);
        ftpClient.enterLocalPassiveMode();
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeToParentDirectory();
            ftpClient.changeWorkingDirectory("/" + idOutroUsuario);
            ftpClient.storeFile(arquivo, inputStream);
//                inputStream.close();
            disconecta();
        } catch (IOException erro) {
            log.error(String.valueOf(erro));
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
            log.error(String.valueOf(erro));
        }
    }



//        public void excluiDiretorio(String id) {
//        ftpClient = conecta();
//        try {
//            ftpClient.removeDirectory("/" + id);
//        } catch (IOException erro) {
//            erro.getMessage();
//        }
//    }



    //    public FTPFile[] listaTodosArquivos() {
//        ftpClient = conecta();
//        ftpClient.enterLocalPassiveMode();
////        ftpClient = verificaDiretorio(id, ftpClient);
//        FTPFile[] files = new FTPFile[0];
//        try {
//            files = ftpClient.listFiles();
////            criarDiretorio(id);
//            return files;
//        }
//        catch (IOException erro) {
//            log.error(String.valueOf(erro));
//        }
//        return files;
//    }
}
