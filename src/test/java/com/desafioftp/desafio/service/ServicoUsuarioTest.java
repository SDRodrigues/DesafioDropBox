package com.desafioftp.desafio.service;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public  class ServicoUsuarioTest {

    @MockBean
    private Repositorio repositorio;

    private ServicoUsuario servicoUsuario;
    private Usuario usuario;

    private static final String ID = "762";
    private static final String NOME = "rodrigues";
    private static final Integer IDADE = 22;
    private static final String PROFISSAO = "Infa Véia";


    @Before
    public void setUp() throws Exception {
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

//    @Test
//    public void lerUsuario() {
//    }
//
//    @Test
//    public void findById() {
//    }
//
//    @Test
//    public void deletaUsuarioId() {
//
//    }
//
//    @Test
//    public void editaUsuario() {
//    }
//
//    @Test
//    public void excluirArquivos() {
//    }
}
