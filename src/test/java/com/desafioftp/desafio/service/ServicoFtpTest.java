package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockftpserver.core.command.CommandNames;
import org.mockftpserver.core.command.ConnectCommandHandler;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.command.*;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

@RunWith(SpringRunner.class)
@PrepareForTest(ServicoFtp.class)
public class ServicoFtpTest {


    private static final String HOST = "127.0.0.1";
    private static final Integer PORTA = 21;
    private static final String USUARIO = "rodrigues";
    private static final String SENHA = "rodrigues";
    private static final String NOTFOUND = "Usuario não encontrado";
    private static final int BINARIO = 2;
    private static final String ARQUIVO = "nomeDoArquivo";
    private static final String ID = "54";
    private static final String OUTROID = "47";

    private static final Integer PAGINAS = 21;
    private static final Integer QUANTIDADE = 21;

    private InputStream inputStream;
    private MockMultipartFile multipartFile;


    private Usuario usuario;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private ServicoFtp servicoFtp;


    @MockBean
    private ServicoUsuario servicoUsuario;

    @MockBean
    private FTPClient ftpClient;


    @MockBean
    private FakeFtpServer fakeFtpServer;

    @MockBean
    private Repositorio repositorio;


    private FTPFile[] ftpFiles;


    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servicoFtp = PowerMockito.spy(new ServicoFtp());
        servicoUsuario = Mockito.mock(ServicoUsuario.class);
        inputStream = Mockito.mock(InputStream.class);
        ftpClient = Mockito.mock(FTPClient.class);
        ftpClient.connect(HOST, PORTA);
        ftpClient.login(USUARIO, SENHA);
        ftpClient.enterLocalPassiveMode();
        usuario = Mockito.mock(Usuario.class);
        fakeFtpServer.start();
        multipartFile = Mockito.mock(MockMultipartFile.class);



    }

    @After
    public void tearDown() throws IOException {
        ftpClient.disconnect();
        ftpClient.logout();
        fakeFtpServer.stop();
    }

    @Test
    public void salvaArquivo() throws IOException, NoSuchMethodException {
        Optional<Usuario> usuario = servicoUsuario.findById(ID);
        Mockito.doNothing().when(servicoFtp).salvaArquivo(ID, multipartFile);
        Method conecta = ServicoFtp.class.getDeclaredMethod("conecta");
        conecta.setAccessible(true);
        Method criaDir = ServicoFtp.class.getDeclaredMethod("criarDiretorio", String.class);
        criaDir.setAccessible(true);
        Mockito.doReturn(true).when(ftpClient).changeWorkingDirectory("/" + ID);
        Mockito.doReturn(true).when(ftpClient).setFileType(BINARIO);
        Assert.assertNotNull(usuario);

    }


    @Test
    public void arquivosCompartilhados() throws NoSuchMethodException {
        Mockito.doNothing().when(servicoFtp).arquivosCompartilhados(ID, OUTROID, ARQUIVO);
        Method download = ServicoFtp.class.getDeclaredMethod(
                "downloadArquivo", String.class, String.class);
        download.setAccessible(true);
        fakeFtpServer.setCommandHandler(CommandNames.RETR, new RetrCommandHandler());
        fakeFtpServer.setCommandHandler(CommandNames.PWD, new PwdCommandHandler());
        fakeFtpServer.setCommandHandler(CommandNames.CWD, new CwdCommandHandler());
        fakeFtpServer.setCommandHandler(CommandNames.STOR, new StorCommandHandler());
        fakeFtpServer.setCommandHandler(CommandNames.QUIT, new QuitCommandHandler());
        Assert.assertNotEquals(ID, OUTROID);

    }

    @Test
    public void excluirArquivos() throws IOException {
        Optional<Usuario> usuario = Optional.of((new Usuario("762", "rodrigues", 22, "shooter")));
        servicoFtp.excluirArquivos(usuario, ARQUIVO);
        fakeFtpServer.setCommandHandler(CommandNames.CONNECT, new ConnectCommandHandler());
        ftpClient.deleteFile("/" + usuario.get().getId() + "/" + ARQUIVO);
        fakeFtpServer.setCommandHandler(CommandNames.DELE, new DeleCommandHandler());
        fakeFtpServer.setCommandHandler(CommandNames.QUIT, new QuitCommandHandler());
        Assert.assertNotNull(usuario);
    }




}