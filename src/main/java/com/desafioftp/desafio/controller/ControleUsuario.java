package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.usuario.Usuario;
import com.desafioftp.desafio.service.Servico;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("v1/Usuario")
public class ControleUsuario {

    private Servico servico;

    @Autowired
    public ControleUsuario(Servico servico) {
        this.servico = servico;
    }

    @GetMapping(value="/usuario", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public List<Usuario> consultar(){
        return this.servico.lerUsuario();
    }


    @PostMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Criar usuário", response= Usuario.class, notes="Essa operação cria o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Criou um usuário com sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public Usuario criaUsuario(@RequestBody Usuario usuario) {
        return servico.criarUsuario(usuario);
    }


    @PutMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Editar usuário", response= Usuario.class, notes="Essa operação edita o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public Usuario editaUsuario(@PathVariable Integer id, Usuario usuario) {
        return servico.editaUsuario(id, usuario);
    }

    @DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Excluir usuário", response= Usuario.class, notes="Essa operação exclui o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Excluiu o usuario", response=Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public void deletaUsuario(@PathVariable Integer id) {servico.deletaUsuarioId(id); }

}
