package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import com.desafioftp.desafio.service.ServicoUsuario;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class ControleUsuarioTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Repositorio repositorio;

    @Mock
    private ServicoUsuario servicoUsuario = new ServicoUsuario();

    @InjectMocks
    private ControleUsuario controleUsuario;

    private UsuarioDto usuarioDto;
    private Usuario usuario;

    private MockMvc mockMvc;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controleUsuario).build();
        servicoUsuario = new ServicoUsuario(repositorio);
        controleUsuario = new ControleUsuario(servicoUsuario);
        usuarioDto = Mockito.mock(UsuarioDto.class);
        usuario = Mockito.mock(Usuario.class);
    }

    @Test
    public void consultar() throws Exception {
        List<Usuario> usuarios = Collections.singletonList(usuario);
        Mockito.when(servicoUsuario.lerUsuario()).thenReturn(usuarios);
        mockMvc.perform(MockMvcRequestBuilders.get("v1/usuarios"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
     controleUsuario.consultar();
    }

    @Test
    public void consultarId() throws ObjetoNaoEncontrado {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
       controleUsuario.consultarId(usuario.getId());
    }

    @Test
    public void criaUsuario() {
        controleUsuario.criaUsuario(usuarioDto);
    }

    @Test
    public void deletaUsuario() throws ObjetoNaoEncontrado {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
        controleUsuario.deletaUsuario(usuario.getId());
    }

    @Test
    public void editaUsuario() throws ObjetoNaoEncontrado {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
      controleUsuario.editaUsuario(usuarioDto);
    }


//
}