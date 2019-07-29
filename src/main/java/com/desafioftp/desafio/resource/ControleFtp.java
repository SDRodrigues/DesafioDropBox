package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("v1/arquivos")
@Api(value = "arquivos")
public class ControleFtp {


     private ServicoFtp servicoFtp;
     private ServicoUsuario servicoUsuario;

    @Autowired
    public ControleFtp( ServicoFtp servicoFtp, ServicoUsuario servicoUsuario) {
        this.servicoFtp = servicoFtp;
        this.servicoUsuario = servicoUsuario;
    }

    @PostMapping(value = "/{id}")
    @ApiOperation(value="Envia arquivos", response= Usuario.class, notes="Essa operação envia os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response= Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
    })
    public void upload(@PathVariable(value = "id") Integer id, @RequestParam MultipartFile arquivo)  {
        this.servicoFtp.enviaArquivo(arquivo, id);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca Arquivos do usuário", response= Usuario.class, notes="Essa operação busca os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso", response= Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
    })
    public ArrayList<Arquivos> buscaArquivos(@PathVariable(value = "id") Integer id )  {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        return this.servicoFtp.buscaArquivos(usuario);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete user file")
    public ResponseEntity deleteFile(@PathVariable Integer id, @RequestBody String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
        return new ResponseEntity(null, HttpStatus.OK);
    }

}
