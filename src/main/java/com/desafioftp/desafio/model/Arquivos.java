package com.desafioftp.desafio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;

@Data
@NoArgsConstructor
public class Arquivos {
    private String nomeArquivo;


    public Arquivos(FTPFile ftpFile) {
        this.setNomeArquivo(ftpFile.getName());
    }
}
