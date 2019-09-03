package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private static final String DIR = "47";



    private static final Integer PAGINAS = 21;
    private static final Integer QUANTIDADE = 21;

    private InputStream inputStream;
    private MockMultipartFile multipartFile;
    private OutputStream outputStream;


    private Usuario usuario;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
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
        outputStream = Mockito.mock(OutputStream.class);
        ftpClient = Mockito.mock(FTPClient.class);
        ftpClient.connect(HOST, PORTA);
        ftpClient.login(USUARIO, SENHA);
        ftpClient.enterLocalPassiveMode();
        usuario = Mockito.mock(Usuario.class);
        fakeFtpServer.start();
        multipartFile = Mockito.mock(MockMultipartFile.class);
        repositorio = Mockito.mock(Repositorio.class);

    }

    @After
    public void tearDown() throws IOException {
        ftpClient.disconnect();
        ftpClient.logout();
        fakeFtpServer.stop();
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void salvaArquivo() throws ObjetoNaoEncontrado {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        servicoFtp.salvaArquivo(usuario.getId(), multipartFile);
        Assert.assertNotNull(usuario);
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void listaArquivosPaginados() {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        servicoFtp.listaArquivosPaginados(usuario.getId(), PAGINAS, QUANTIDADE);
        Mockito.when(false).thenThrow(new ObjetoNaoEncontrado(NOTFOUND));
        Assert.assertNotNull(usuario);

    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void arquivosCompartilhados() throws ObjetoNaoEncontrado {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        Usuario usuario2 = (new Usuario("407", "rodrigues", 22, "shooter"));
        servicoFtp.arquivosCompartilhados(ID, OUTROID, ARQUIVO);
        exception.expect(IOException.class);
        Assert.assertNotEquals(usuario, usuario2);


    }

    @Test
    public void excluirArquivos() throws IOException {
        Optional<Usuario> usuario = Optional.of((new Usuario("762", "rodrigues", 22, "shooter")));
        servicoFtp.excluirArquivos(usuario, ARQUIVO);
        ftpClient.deleteFile("/" + usuario.get().getId() + "/" + ARQUIVO);
        Mockito.doThrow(IOException.class).when(ftpClient).deleteFile("/" + usuario.get().getId() + "/" + ARQUIVO);
        Assert.assertNotNull(usuario);
    }






}