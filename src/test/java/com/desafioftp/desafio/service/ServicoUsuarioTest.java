package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ServicoUsuarioTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private Repositorio repositorio;
    private ServicoUsuario servicoUsuario;
    private static final String ID = "762";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servicoUsuario = new ServicoUsuario(repositorio);
    }

    @Test
    public void criandoUsuario() {
        //given
        var usuario = new Usuario(ID, "rodrigues2", 22, "shooter");
        Mockito.when(repositorio.insert(usuario)).thenReturn(usuario);
        //when
        Usuario actual = servicoUsuario.criarUsuario(usuario);
        //then
        assertEquals(usuario.getId(), actual.getId());
        assertEquals(usuario.getIdade(), actual.getIdade());
        assertEquals(usuario.getProfissao(), actual.getProfissao());
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
        Mockito.when(repositorio.findById(ID)).thenReturn(Optional.ofNullable(null));
        servicoUsuario.findById(ID);
    }

    @Test
    public void excluindoUsuarioComSucesso() {
        //given
        var id = "312312";
        Optional<Usuario> usuario = Optional.of(new Usuario(id, "rodrigues", 22, "shooter"));
        Mockito.when(repositorio.findById(id)).thenReturn(usuario);
        Mockito.doNothing().when(repositorio).deleteById(id);
        //when
        servicoUsuario.deletaUsuarioId(id);
        //then
        Mockito.verify(repositorio, Mockito.times(1)).findById(id);
        Mockito.verify(repositorio, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void falhaAoExcluirUsuario() {
        //given
        var id = "312312";
        Mockito.when(repositorio.findById(id)).thenReturn(Optional.empty());
        Mockito.doNothing().when(repositorio).deleteById(id);
        //when
        servicoUsuario.deletaUsuarioId(id);
    }

    @Test
    public void editandoUsuarioComSucesso() {
        //given
        var usuarioVelho = new Usuario(ID, "rodrigues", 22, "shooter");
        var usuario = new Usuario(ID, "rodrigues2", 22, "shooter");
        //when
        Mockito.when(repositorio.findById(usuario.getId())).thenReturn(Optional.ofNullable(usuarioVelho));
        Mockito.when(repositorio.save(usuario)).thenReturn(usuario);
        //then
        Usuario usuarioSalvo = servicoUsuario.editaUsuario(usuario);
        assertEquals(usuarioSalvo.getId(), usuario.getId());
    }

    @Test(expected = ObjetoNaoEncontrado.class)
    public void falhaAoeEditarUsuario() {
        //given
        var usuario = new Usuario(ID, "rodrigues2", 22, "shooter");
        Mockito.when(repositorio.findById(usuario.getId())).thenReturn(Optional.empty());
        Mockito.when(repositorio.save(usuario)).thenReturn(usuario);
        //when
        servicoUsuario.editaUsuario(usuario);
    }
}
