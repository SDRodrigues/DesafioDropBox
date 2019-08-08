package com.desafioftp.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;
import java.io.File;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Arquivos {
    private String nomeArquivo;

    public Arquivos(File file) {
        nomeArquivo = file.getName();
    }

    public Arquivos(FTPFile file) {
        nomeArquivo = file.getName();
    }



}
