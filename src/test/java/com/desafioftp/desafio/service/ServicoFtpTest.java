package com.desafioftp.desafio.service;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class ServicoFtpTest {

    @Mock
    FTPClient ftpClient;
    ServicoUsuario servicoUsuario;

    private ServicoFtp servicoFtp;

    @Before
    public void setUp() {
        servicoFtp = new ServicoFtp(servicoUsuario);
    }

    @Test
    public void salvaArquivo() {
    }

    @Test
    public void listaTodosArquivos() {
    }

    @Test
    public void listaArquivosPaginados() {
    }

    @Test
    public void excluirArquivos() {
    }

    @Test
    public void arquivosCompartilhados() {
    }
}