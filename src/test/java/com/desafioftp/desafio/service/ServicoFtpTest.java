package com.desafioftp.desafio.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ServicoFtpTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ServicoFtp.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void salvaArquivo() {
    }

    @Test
    public void listaTodosArquivos() {
    }

    @Test
    public void listaArquivosPaginados() {
    }

    @Test
    public void downloadArquivo() {
    }

    @Test
    public void excluirArquivos() {
    }

    @Test
    public void excluiDiretorio() {
    }

    @Test
    public void arquivosCompartilhados() {
    }
}
