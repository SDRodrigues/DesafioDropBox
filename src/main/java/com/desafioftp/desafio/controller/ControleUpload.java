package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoUpload;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
@RequestMapping("v1/arquivos")
public class ControleUpload {

     private ServicoUpload servicoUpload;
     private ServicoUsuario servicoUsuario;

    @Autowired
    public ControleUpload(ServicoUpload servicoUpload, ServicoUsuario servicoUsuario) {
        this.servicoUpload = servicoUpload;
        this.servicoUsuario = servicoUsuario;
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    @ApiOperation(value="Envia arquivos", response= UsuarioDto.class, notes="Essa operação envia os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response= UsuarioDto.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = UsuarioDto.class),
            @ApiResponse(code=500, message="Erro interno", response= UsuarioDto.class)
    })
    public void upload(@PathVariable(value = "id") Integer id, @RequestParam MultipartFile arquivo)  {
        Optional<Usuario> usuarioAux = servicoUsuario.lerUsuarioId(id);
        Usuario usuario = usuarioAux.get();

        this.servicoUpload.enviarArquivos(arquivo,usuario);
    }

    @GetMapping()
    @ResponseBody
    @ApiOperation(value="Buscar usuário", response= UsuarioDto.class, notes="Essa operação busca o usuario.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response= UsuarioDto.class),
            @ApiResponse(code=404, message = "Não encontrou usuário", response = UsuarioDto.class),
            @ApiResponse(code=500, message="Erro interno", response= UsuarioDto.class)
    })
    public boolean listar(){
        return this.servicoUpload.listarArquivos();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value="Excluir usuário", response= Usuario.class, notes="Essa operação exclui o model.")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="Excluiu o model", response=Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
    })
    public void deletaArquivo(@PathVariable @NotNull String arquivo, UsuarioDto usuario) {
        servicoUpload.excluirArquivos(arquivo, usuario); }
}
