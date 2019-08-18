package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
public class ServicoFtpTest {

    @Mock
    FTPClient ftpClient;
    ServicoUsuario servicoUsuario;

    private Usuario usuario;
    private FakeFtpServer fakeFtpServer;

    private ServicoFtp servicoFtp;
    private static final String ID = "762";
    private static final String NOME = "rodrigues";
    private static final Integer IDADE = 22;
    private static final String PROFISSAO = "Infa Véia";
    private static final Integer PAGINAS = 1;
    private static final String HOST = "127.0.0.1";
    private static final Integer PORTA = 21;
    private static final String USER = "rodrigues";
    private static final String SENHA = "rodrigues";

    @Before
    public void setUp() {
        servicoFtp = new ServicoFtp(servicoUsuario);
        ftpClient = new FTPClient();
        Usuario usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNome(NOME);
        usuario.setIdade(IDADE);
        usuario.setProfissao(PROFISSAO);

    }

    @After
    public void teardown() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
        fakeFtpServer.stop();
    }

    @Test
    public void salvaArquivo() {
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