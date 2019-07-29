package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/usuarios")
@Api(value = "usuarios")
public class ControleUsuario {

    private ServicoUsuario servicoUsuario;

    @Autowired
    public ControleUsuario(ServicoUsuario servicoUsuario) {
        this.servicoUsuario = servicoUsuario;
    }

    @GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca os usuários.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public List<Usuario> consultar(){
        return this.servicoUsuario.lerUsuario();
    }

    @GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca um usuário especifico.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public Optional<Usuario> consultarId(@PathVariable Integer id){ return this.servicoUsuario.lerUsuarioId(id);
    }


    @PostMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Criar usuário", response= Usuario.class, notes="Essa operação cria o model.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Criou um usuário com sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public Usuario criaUsuario(@RequestBody Usuario usuario) {
        return servicoUsuario.criarUsuario(usuario);
    }

    @DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Excluir usuário", response= Usuario.class, notes="Essa operação exclui o model.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Excluiu o model", response=Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public void deletaUsuario(@PathVariable Integer id) {
        servicoUsuario.deletaUsuarioId(id);
    }

    @PutMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Editar usuário", response= Usuario.class, notes="Essa operação edita o model.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response=Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public Usuario editaUsuario(@PathVariable Integer id, Usuario usuario) {
        return servicoUsuario.editaUsuario(id, usuario);
    }
}
