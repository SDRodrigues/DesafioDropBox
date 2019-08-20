package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import com.desafioftp.desafio.service.ServicoUsuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
public class ControleUsuarioTest {

    @Mock
    private Repositorio repositorio;
    private ServicoUsuario servicoUsuario;

    @InjectMocks
    private ControleUsuario controleUsuario;

    private UsuarioDto usuarioDto;
    private Usuario usuario;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controleUsuario)
                .build();
        servicoUsuario = new ServicoUsuario(repositorio);
        controleUsuario = new ControleUsuario(servicoUsuario);
        usuarioDto = Mockito.mock(UsuarioDto.class);
        usuario = Mockito.mock(Usuario.class);
    }

    @Test
    public void consultar() {
     controleUsuario.consultar();
    }

    @Test
    public void consultarId() {
       controleUsuario.consultarId(usuario.getId());
    }

    @Test
    public void criaUsuario() {
        controleUsuario.criaUsuario(usuarioDto);
    }

    @Test
    public void deletaUsuario() {
        controleUsuario.deletaUsuario(usuario.getId());
    }

    @Test
    public void editaUsuario() {
      controleUsuario.editaUsuario(usuarioDto);
    }


//
}