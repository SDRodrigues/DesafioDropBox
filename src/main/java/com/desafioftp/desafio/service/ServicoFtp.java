package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@NoArgsConstructor
public class ServicoFtp {
        private FTPClient ftpClient;
        private static final String HOST = "127.0.0.1";
        private static final Integer PORTA = 21;
        private static final String USUARIO = "rodrigues";
        private static final String SENHA = "rodrigues";
        private ServicoUsuario servicoUsuario;
        private static final String NOTFOUND = "Usuario não encontrado";



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
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        if (usuario.isPresent()) {
            String recebeId = usuario.get().getId();
            ftpClient = conecta();
            ftpClient.enterLocalPassiveMode();
            criarDiretorio(recebeId);
            try {
                ftpClient.changeWorkingDirectory("/" + recebeId);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.storeFile(file.getOriginalFilename(), file.getInputStream());
                disconecta();
            } catch (IOException erro) {
                log.error(String.valueOf(erro));
            }
        } else {
            throw new ObjetoNaoEncontrado(NOTFOUND);
        }
    }


    public Page<FTPFile> listaArquivosPaginados(String id, Integer paginas, Integer filtro) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        if (usuario.isPresent()) {
            String recebeId = usuario.get().getId();
            ftpClient = conecta();
            ftpClient = criarDiretorio(recebeId);
            try {
                ftpClient.changeWorkingDirectory("/" + recebeId);
                return criouArquivosPaginados(ftpClient.listFiles(), paginas, filtro);
            } catch (IOException erro) {
                log.error(String.valueOf(erro));
            }
        } else {
            throw new ObjetoNaoEncontrado(NOTFOUND);
        }
        return null;
    }

    public void arquivosCompartilhados(String idUsuario, String idOutroUsuario, String arquivo) {
        Optional<Usuario> usuarioEnvia = servicoUsuario.findById(idUsuario);
        Optional<Usuario> usuarioRecebe = servicoUsuario.findById(idOutroUsuario);
        if (usuarioEnvia.isPresent() && usuarioRecebe.isPresent() ) {
            ftpClient = new FTPClient();
            downloadArquivo(arquivo, idUsuario);
            InputStream inputStream = IOUtils.toInputStream(arquivo, StandardCharsets.UTF_8);
            ftpClient.enterLocalPassiveMode();
            try {
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.changeToParentDirectory();
                ftpClient.changeWorkingDirectory("/" + idOutroUsuario);
                ftpClient.storeFile(arquivo, inputStream);
                inputStream.close();
                disconecta();
            } catch (IOException erro) {
                log.error(String.valueOf(erro));
            }
        } else {
            throw new ObjetoNaoEncontrado(NOTFOUND);
        }
    }

    public void excluirArquivos(Optional<Usuario> usuario, String nomeArquivo) {
        ftpClient = conecta();
        if (usuario.isPresent()) {
            String recebeId = usuario.get().getId();
            try {
                ftpClient.deleteFile("/" + recebeId + "/" + nomeArquivo);
                disconecta();
            } catch (IOException erro) {
                log.error(String.valueOf(erro));
            }
        }
    }


}
