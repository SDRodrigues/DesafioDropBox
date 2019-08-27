package com.desafioftp.desafio.service;

import com.desafioftp.desafio.repository.Repositorio;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.core.command.CommandNames;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.command.DeleCommandHandler;
import org.mockftpserver.fake.command.ListCommandHandler;
import org.mockftpserver.fake.command.RetrCommandHandler;
import org.mockftpserver.fake.command.StorCommandHandler;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ServicoFtpTest {

    @InjectMocks
    private ServicoFtp servicoFtp;
    private ServicoUsuario servicoUsuario;
    private FTPClient ftpClient;

    private FakeFtpServer fakeFtpServer;
    private final static String ID = "762";
    private MockMultipartFile multipartFile;

    @Mock
    private Repositorio repositorio;

    @Before
    public void setUp() {
        fakeFtpServer = new FakeFtpServer();
        servicoUsuario = new ServicoUsuario(repositorio);
        servicoFtp = new ServicoFtp(servicoUsuario);
    }

    @Test
    public void salvaArquivo() {
        fakeFtpServer.setCommandHandler(CommandNames.STOR, new StorCommandHandler());
    }

    @Test
    public void listaArquivosPaginados() {
        fakeFtpServer.setCommandHandler(CommandNames.LIST, new ListCommandHandler());
    }

    @Test
    public void arquivosCompartilhados() {
        fakeFtpServer.setCommandHandler(CommandNames.RETR, new RetrCommandHandler());
    }

    @Test
    public void excluirArquivos() {
        fakeFtpServer.setCommandHandler(CommandNames.DELE, new DeleCommandHandler());
    }

}