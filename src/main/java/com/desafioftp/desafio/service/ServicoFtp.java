package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.server.Conexao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ServicoFtp {
        private FTPClient ftpClient;
        private Conexao conexao;

        @Autowired
    public ServicoFtp(Conexao conexao) {
        this.conexao = conexao;
    }

    public String[] nomeDiretorio(String diretorio) {
            String[] nomeDir = null;
            try {
                conexao.conecta();
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

        public FTPFile[] buscaArquivos(String diretorio) {
            FTPFile[] arquivosConfig = null;
            try {
                conexao.conecta();
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

        public boolean enviaArquivo(MultipartFile arquivo, Usuario usuario) {
            try {
                conexao.conecta();
                ftpClient.enterLocalPassiveMode();
                if (ftpClient.storeFile(arquivo.getOriginalFilename(), arquivo.getInputStream())) {
                    return true;
                }
            }catch(IOException e) {
                e.printStackTrace();
            } finally {
                conexao.disconecta();
            }
            return false;
        }
        public void buscaAquivo(String arquivo) {
            try {
                conexao.conecta();
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType( FTPClient.BINARY_FILE_TYPE );
                OutputStream os = new FileOutputStream(arquivo);
                ftpClient.retrieveFile(arquivo, os );
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conexao.disconecta();
            }
        }

        public void excluirArquivos(String arquivo) {
            try {
                conexao.conecta();
                ftpClient.deleteFile(arquivo);
            }
            catch (IOException erro) {
                erro.getMessage();
            }
        }



    }
