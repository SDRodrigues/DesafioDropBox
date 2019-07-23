package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.usuario.UsuarioUpload;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("v1/arquivos")
public class ControleFtp {

     private ServicoFtp servicoFtp;

    @Autowired
    public ControleFtp(ServicoFtp servicoFtp) {
        this.servicoFtp = servicoFtp;
    }

    @PostMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ApiOperation(value="Envia arquivos", response= UsuarioUpload.class, notes="Essa operação envia os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response= UsuarioUpload.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = UsuarioUpload.class),
            @ApiResponse(code=500, message="Erro interno", response= UsuarioUpload.class)
    })
    public void upload(@RequestParam @NotNull MultipartFile arquivo, @NotNull UsuarioUpload usuario)  {
        this.servicoFtp.enviarArquivos(arquivo, usuario);
    }

}
