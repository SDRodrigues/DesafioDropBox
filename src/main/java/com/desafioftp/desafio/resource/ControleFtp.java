package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.*;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("v1/arquivos")
@Api(value = "arquivos")
public class ControleFtp {

     private ServicoFtp servicoFtp;
     private ServicoUsuario servicoUsuario;

     @Autowired
    public ControleFtp(ServicoFtp servicoFtp, ServicoUsuario servicoUsuario) {
        this.servicoFtp = servicoFtp;
        this.servicoUsuario = servicoUsuario;
    }

    @PostMapping(value = "/{id}")
    @ApiOperation(value="Envia arquivos")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso"),
            @ApiResponse(code=404, message = "Não encontrou arquivos"),
            @ApiResponse(code=500, message="Erro interno")
    })
    public ResponseEntity<String> uploadArquivo(@PathVariable String id, @RequestBody MultipartFile arquivo)  {
        this.servicoFtp.salvaArquivo(servicoUsuario.findById(id).get().getId(), arquivo);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca Arquivos do usuário")
    public FTPFile[] listaUpload(@PathVariable String id ) {
        return this.servicoFtp.listaTodosArquivos(servicoUsuario.findById(id).get().getId());
    }

    @ApiOperation(value = "download dos arquivos")
    @GetMapping(value = "/downloads/{id}")
    public void downloadArquivo(
            @ApiParam @PathVariable String id,
            @ApiParam @RequestParam String arquivo ){
        this.servicoFtp.downloadArquivo(arquivo,servicoUsuario.findById(id).get().getId());
    }

    @GetMapping(value = "/paginas/{id}")
    @ApiOperation(value="Busca arquivos com filtros do usuario")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso"),
            @ApiResponse(code=404, message = "Não encontrou arquivos"),
            @ApiResponse(code=500, message="Erro interno")
    })
    public Page<FTPFile> arquivosPaginados(@PathVariable String id,
                                            @RequestParam Integer paginas,
                                            @RequestParam Integer quantidade ) {
         return servicoFtp.listaArquivosPaginados(servicoUsuario.findById(id).get().getId(), paginas, quantidade);
    }

    @GetMapping(value = "/compartilha/{idUsuarioEnvia}/arquivos/{idUsuarioRecebe}")
    @ApiOperation(value="compartilha arquivos")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso"),
            @ApiResponse(code=404, message = "Não encontrou arquivos"),
            @ApiResponse(code=500, message="Erro interno")
    })
    public void compartilhaArquivos(@PathVariable String idUsuarioEnvia,
                                             @RequestParam String arquivo,
                                             @PathVariable String idUsuarioRecebe
                                             ) {
         servicoFtp.arquivosCompartilhados(servicoUsuario.findById(idUsuarioEnvia).get().getId(),
                                                servicoUsuario.findById(idUsuarioRecebe).get().getId(),
                                                arquivo);
    }




    @DeleteMapping(value = "/{id}")
    @ApiParam(name = "id", required = true)
    @ApiOperation(value = "Deleta arquivos do usuário")
    public void deletar(@PathVariable String id, @RequestParam String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
    }



}
