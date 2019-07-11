package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Usuario")
public class Controle {

    @Autowired
    private Repositorio repositorio;


    @GetMapping(value="/model", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=500, message="Não conseguiu buscar usuário", response=Usuario.class)
    })
    public List<Usuario> consultar(){
        return this.repositorio.findAll();
    }






}
