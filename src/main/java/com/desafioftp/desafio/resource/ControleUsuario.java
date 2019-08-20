package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public List<Usuario> consultar(){
        return this.servicoUsuario.lerUsuario();
    }

    @GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca um usuário especifico.")
    public Optional<Usuario> consultarId(@PathVariable String id){ return this.servicoUsuario.findById(id);
    }


    @PostMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Criar usuário", response= Usuario.class, notes="Essa operação cria o usuario.")
    public Usuario criaUsuario(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = servicoUsuario.dtoParaUsuario(usuarioDto);
         return servicoUsuario.criarUsuario(usuario);
    }

    @DeleteMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Excluir usuário", response= Usuario.class, notes="Essa operação exclui o usuario.")
    public void deletaUsuario(@PathVariable String id) {
        servicoUsuario.deletaUsuarioId(id);
    }

    @PutMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="Editar usuário", response= Usuario.class, notes="Essa operação edita o usuario.")
    public Usuario editaUsuario(@RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = servicoUsuario.dtoParaUsuario(usuarioDto);
        return servicoUsuario.editaUsuario(usuario);
    }
}
