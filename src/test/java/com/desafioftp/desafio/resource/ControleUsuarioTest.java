package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import com.desafioftp.desafio.service.ServicoUsuario;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControleUsuarioTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private Repositorio repositorio;

    @MockBean
    private ServicoUsuario servicoUsuario;

    @InjectMocks
    private ControleUsuario controleUsuario;

    private UsuarioDto usuarioDto;
    private Usuario usuario;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controleUsuario).build();
        servicoUsuario = new ServicoUsuario(repositorio);
        controleUsuario = new ControleUsuario(servicoUsuario);
        usuarioDto = Mockito.mock(UsuarioDto.class);
        usuario = Mockito.mock(Usuario.class);
    }


    @Test
    public void listUsuarios() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/usuarios",  String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void list() {
        List<Usuario> usuarios = asList(new Usuario("762", "rodrigues", 22, "shooter"),
        new Usuario("407", "rodrigues", 30, "shooter"));
        BDDMockito.when(repositorio.findAll()).thenReturn(usuarios);
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/usuarios", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void listId() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        BDDMockito.when(repositorio.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/usuarios/762", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void criandoUsuario() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        BDDMockito.when(repositorio.insert(usuario)).thenReturn(usuario);
        ResponseEntity<Usuario> response = restTemplate.postForEntity("/v1/usuarios", usuario, Usuario.class);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    public void excluiUsuario() {
        Usuario usuario = new Usuario("762", "rodrigues", 22, "shooter");
        BDDMockito.when(repositorio.findById(usuario.getId())).thenReturn(java.util.Optional.of(usuario));
        BDDMockito.doNothing().when(repositorio).deleteById("762");
        ResponseEntity<String> response = restTemplate.exchange("/v1/usuarios/762", HttpMethod.DELETE,
                null, String.class, 1);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    }



    @Test
    public void editaUsuario() throws ObjetoNaoEncontrado {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
        controleUsuario.editaUsuario(usuarioDto);
        servicoUsuario.editaUsuario(usuario);
    }

}