package com.desafioftp.desafio.controller;

import com.desafioftp.desafio.service.ServicoArquivo;
import com.desafioftp.desafio.usuario.UsuarioUpload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("v1/Arquivos")
public class ControleFtp {

     private ServicoArquivo servicoArquivo;

    @Autowired
    public ControleFtp(ServicoArquivo servicoArquivo) {
        this.servicoArquivo = servicoArquivo;
    }

//    @PostMapping(value = "/usuariosUpload", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    @ApiOperation(value="Envia arquivos", response= UsuarioUpload.class, notes="Essa operação envia os arquivos" +
//            " do usuário.")
//    @ApiResponses(value= {
//            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response=UsuarioUpload.class),
//            @ApiResponse(code=404, message = "Não encontrou arquivos", response = UsuarioUpload.class),
//            @ApiResponse(code=500, message="Erro interno", response=UsuarioUpload.class)
//    })
//    public UsuarioUpload enviarArquivos() {
//        return servicoArquivo.enviarArquivos();
//    }
}
