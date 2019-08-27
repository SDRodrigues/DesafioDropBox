package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ServicoUsuarioTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private Repositorio repositorio;

    private ServicoUsuario servicoUsuario;
    private Usuario usuario;
    private UsuarioDto usuarioDto;
    private Usuario novoUsuario;

    private static final String ID = "762";


    @Before
    public void setUp() {
        servicoUsuario = new ServicoUsuario(repositorio);
        usuarioDto = Mockito.mock(UsuarioDto.class);
        novoUsuario = Mockito.mock(Usuario.class);
    }

    @Test
    public void criarUsuario() {
        servicoUsuario.criarUsuario(usuario);
        Mockito.verify(repositorio).insert(usuario);
        assertEquals(usuario, usuario);
    }

    @Test
    public void lerUsuario() {
        servicoUsuario.lerUsuario();
        Mockito.verify(repositorio).findAll();
    }

    @Test
    public void BuscaTodosUsuarios() {
        Mockito.when(repositorio.findAll()).thenReturn(Stream.of
                (new Usuario("762", "rodrigues", 22, "shooter"),
                 new Usuario("407", "rodrigues", 30, "shooter"))
                .collect(Collectors.toList()));
            assertEquals(2, servicoUsuario.lerUsuario().size());
    }

    @Test
    public void findById() throws ObjetoNaoEncontrado {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
        Mockito.when(repositorio.findById(ID)).thenReturn(Optional.ofNullable(usuario));
        servicoUsuario.findById(ID);
    }

    @Test
    public void criandoUsuario() {
        Mockito.when(repositorio.insert(usuario)).thenReturn(usuario);
        assertEquals(usuario, servicoUsuario.criarUsuario(usuario));
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void excluindoUsuario() throws ObjetoNaoEncontrado {
        servicoUsuario.deletaUsuarioId(ID);
        Mockito.verify(repositorio, Mockito.times(1)).deleteById(ID);
    }


    @Test
    public void editandoUsuario() {
        exception.expect(ObjetoNaoEncontrado.class);
        exception.expectMessage("Usuario não encontrado");
        servicoUsuario.editaUsuario(novoUsuario);
        Mockito.when(repositorio.findById(usuario.getId())).thenReturn(Optional.ofNullable(usuario));
    }

    @Test
    public void dtoParaUsuario() {
        servicoUsuario.dtoParaUsuario(usuarioDto);
        assertNotNull(usuarioDto);
    }

}
