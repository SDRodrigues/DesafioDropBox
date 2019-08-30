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
import org.springframework.web.multipart.MultipartFile;
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
    private static final String DIR = "47";



    private static final Integer PAGINAS = 21;
    private static final Integer QUANTIDADE = 21;

    private InputStream inputStream;
    private MockMultipartFile multipartFile;


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
    public void salvaArquivo() throws IOException, ObjetoNaoEncontrado, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        servicoFtp.salvaArquivo(usuario.getId(), multipartFile);
        Method conecta = ServicoFtp.class.getDeclaredMethod("conecta");
        conecta.setAccessible(true);
        Method criaDir = ServicoFtp.class.getDeclaredMethod("criarDiretorio", String.class);
        criaDir.setAccessible(true);
        criaDir.invoke(ID);
        ftpClient.changeWorkingDirectory("/");
        ftpClient.setFileType(BINARIO);
        ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        Assert.assertNotNull(usuario);
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void listaArquivosPaginados() throws IOException, NoSuchMethodException {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        ftpClient.changeWorkingDirectory("/" + usuario.getId());
        Method conecta = ServicoFtp.class.getDeclaredMethod("conecta");
        conecta.setAccessible(true);
        Method verificaDiretorio = ServicoFtp.class.getDeclaredMethod("verificaDiretorio", String.class, FTPClient.class);
        verificaDiretorio.setAccessible(true);
        Assert.assertNotNull(usuario);

    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void arquivosCompartilhados() throws ObjetoNaoEncontrado, NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        servicoFtp = new ServicoFtp(servicoUsuario);
        Usuario usuario = (new Usuario("762", "rodrigues", 22, "shooter"));
        Usuario usuario2 = (new Usuario("407", "rodrigues", 22, "shooter"));
        servicoFtp.arquivosCompartilhados(ID, OUTROID, ARQUIVO);
        Method download = ServicoFtp.class.getDeclaredMethod("downloadArquivo", String.class, String.class);
        download.setAccessible(true);
        Mockito.doReturn(true).when(ftpClient).setFileType(BINARIO);
        Mockito.doReturn(true).when(ftpClient).changeToParentDirectory();
        Mockito.doReturn(true).when(ftpClient).changeWorkingDirectory("/");
        ftpClient.storeFile(multipartFile.getOriginalFilename(), multipartFile.getInputStream());
        Method disconecta = ServicoFtp.class.getDeclaredMethod("disconecta");
        disconecta.setAccessible(true);
        Assert.assertNotEquals(usuario, usuario2);


    }

    @Test
    public void excluirArquivos() throws IOException {
        Optional<Usuario> usuario = Optional.of((new Usuario("762", "rodrigues", 22, "shooter")));
        servicoFtp.excluirArquivos(usuario, ARQUIVO);
        ftpClient.deleteFile("/" + usuario.get().getId() + "/" + ARQUIVO);
        Assert.assertNotNull(usuario);
    }




}