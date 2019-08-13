package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ServicoUsuarioTest {

    @Mock
    private Repositorio repositorio;

    private ServicoUsuario servicoUsuario;
    private Usuario usuario;

    private static final String ID = "762";
    private static final String NOME = "rodrigues";
    private static final Integer IDADE = 22;
    private static final String PROFISSAO = "Infa Véia";


    @Before
    public void setUp() {
        servicoUsuario = new ServicoUsuario(repositorio);
        usuario = new Usuario();
        usuario.setId(ID);
        usuario.setNome(NOME);
        usuario.setIdade(IDADE);
        usuario.setProfissao(PROFISSAO);
    }

    @Test
    public void criarUsuario() {
        servicoUsuario.criarUsuario(usuario);
        Mockito.verify(repositorio).insert(usuario);
    }

    @Test
    public void lerUsuario() {
        servicoUsuario.lerUsuario();
        Mockito.verify(repositorio).findAll();
    }

    @Test
    public void findById() {
        servicoUsuario.findById(ID);
        BDDMockito.when(repositorio.findById(ID)).thenReturn(Optional.of(usuario));
    }

    @Test
    public void deletaUsuarioId() {
    }

    @Test
    public void editaUsuario() {
    }
}
