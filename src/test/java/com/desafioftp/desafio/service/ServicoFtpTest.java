package com.desafioftp.desafio.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public  class ServicoFtpTest {

    @MockBean
    private ServicoUsuario servicoUsuario;

    @Before
    public void setUp() throws Exception {

    }

//    @Test
//    public void salvaArquivo() {
//    }
//
//    @Test
//    public void listaTodosArquivos() {
//    }
//
//    @Test
//    public void listaArquivosPaginados() {
//    }
//
//    @Test
//    public void downloadArquivo() {
//    }
//
//    @Test
//    public void excluirArquivos() {
//    }
//
//    @Test
//    public void excluiDiretorio() {
//    }
//
//    @Test
//    public void arquivosCompartilhados() {
//    }
}
