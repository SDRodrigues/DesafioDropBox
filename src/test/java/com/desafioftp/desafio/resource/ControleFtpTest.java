package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControleFtpTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ServicoUsuario servicoUsuario;

    @MockBean
    private ServicoFtp servicoFtp;


    @InjectMocks
    private ControleFtp controleFtp;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controleFtp).build();
        servicoUsuario = Mockito.mock(ServicoUsuario.class);

    }

    @Test
    public void uploadArquivo() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        BDDMockito.doNothing().when(servicoFtp).salvaArquivo(usuario.getId(), multipartFile);
        ResponseEntity<String> response = restTemplate.exchange("/v1/arquivos/{id}", HttpMethod.POST,
                null, String.class, 1);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void arquivosPaginados() {
        PageImpl ftpFiles = Mockito.mock(PageImpl.class);
        Mockito.when(servicoFtp.listaArquivosPaginados("2", 1, 4)).thenReturn(ftpFiles);
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/arquivos/1/2/3", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void compartilhaArquivos() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        Usuario usuario2 = new Usuario("762", "rodrigues", 22, "shooter");
        BDDMockito.when(servicoUsuario.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        BDDMockito.when(servicoUsuario.findById(usuario2.getId())).thenReturn(java.util.Optional.of(usuario2));
        BDDMockito.doNothing().when(servicoFtp).arquivosCompartilhados(usuario.getId(), usuario2.getId(), "3");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/arquivos/compartilha/44/arquivo/77", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    public void deletar() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        BDDMockito.when(servicoUsuario.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        BDDMockito.doNothing().when(servicoFtp).excluirArquivos(java.util.Optional.of(usuario), "nomeArquivo");
        ResponseEntity<String> response = restTemplate.exchange("/v1/arquivos/{id}", HttpMethod.DELETE,
                null, String.class, 1);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }
}