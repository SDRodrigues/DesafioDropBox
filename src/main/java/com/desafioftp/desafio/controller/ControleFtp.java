package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.usuario.Usuario;
import com.desafioftp.desafio.usuario.UsuarioDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
@RequestMapping("v1/Arquivos")
public class ControleFtp {

     private ServicoFtp servicoFtp;

    @Autowired
    public ControleFtp(ServicoFtp servicoFtp) {
        this.servicoFtp = servicoFtp;
    }

    @PostMapping(value = "/usuariosUpload", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Envia arquivos", response= UsuarioDto.class, notes="Essa operação envia os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response= UsuarioDto.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = UsuarioDto.class),
            @ApiResponse(code=500, message="Erro interno", response= UsuarioDto.class)
    })
    public void upload(@RequestParam @NotNull MultipartFile arquivo, @NotNull UsuarioDto usuario)  {
        this.servicoFtp.enviarArquivos(arquivo, usuario);
    }

}
