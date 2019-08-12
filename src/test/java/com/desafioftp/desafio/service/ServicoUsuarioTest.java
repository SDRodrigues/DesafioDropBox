package com.desafioftp.desafio.service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ServicoUsuarioTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ServicoUsuario.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void criarUsuario() {
    }

    @Test
    public void lerUsuario() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void deletaUsuarioId(String id) {

    }

    @Test
    public void editaUsuario() {
    }

    @Test
    public void excluirArquivos() {
    }
}
