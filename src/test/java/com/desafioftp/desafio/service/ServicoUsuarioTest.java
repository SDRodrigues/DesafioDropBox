package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ServicoUsuarioTest {

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
        usuario = new Usuario();
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
    public void findById() {
        Mockito.when(repositorio.findById(ID)).thenReturn(Optional.ofNullable(usuario));
        servicoUsuario.findById(ID);
    }

    @Test
    public void deletaUsuarioId() {
        Mockito.when(repositorio.findById(ID)).thenReturn(Optional.ofNullable(usuario));
        servicoUsuario.findById(ID);
        servicoUsuario.deletaUsuarioId(ID);
        Mockito.verify(repositorio).deleteById(ID);
    }

    @Test
    public void editaUsuario() {
        Mockito.when(repositorio.findById(usuario.getId())).thenReturn(Optional.ofNullable(usuario));
        servicoUsuario.editaUsuario(novoUsuario);
    }

    @Test
    public void dtoParaUsuario() {
        servicoUsuario.dtoParaUsuario(usuarioDto);
    }
}
