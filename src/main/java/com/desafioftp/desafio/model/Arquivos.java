package com.desafioftp.desafio.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;

@Data
@NoArgsConstructor
public class Arquivos {
    private String nomeArquivo;

    public Arquivos(File file) {
        nomeArquivo = file.getName();
    }

    public Arquivos(FTPFile file) {
    }

    public Arquivos recebeFile(File file) throws IOException {
        Arquivos arquivos = new Arquivos();
        arquivos.nomeArquivo = file.getName();
        return arquivos;
    }


}
