package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.ArquivoDownload;
import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        this.servicoFtp.storeFile(servicoUsuario.findById(id).get().getId(), arquivo);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

        @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca Arquivos do usuário")
    public ArrayList<Arquivos> buscaArquivos(@PathVariable(value = "id") String id )  {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        return this.servicoFtp.buscaArquivos(usuario);
    }

//    @GetMapping(value = "/{id}")
//    @ApiOperation(value="Busca Arquivos do usuário")
//    public void buscaArquivos(@PathVariable(value = "id") String id )  {
//          this.servicoFtp.listaArquivos(id);
//    }

//    @GetMapping(value = "/{id}")
//    @ApiOperation(value="Busca Arquivos do usuário")
//    public FTPFile[] listaUpload(@PathVariable(value = "id") String id ) {
//        Optional<Usuario> usuario = servicoUsuario.findById(id);
//        return this.servicoFtp.listaUpload(usuario);
//    }

    @GetMapping(value = "/paginas/{id}")
    @ApiOperation(value="Busca arquivos com filtros do usuario")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso"),
            @ApiResponse(code=404, message = "Não encontrou arquivos"),
            @ApiResponse(code=500, message="Erro interno")
    })
    public void arquivosPaginados(@PathVariable(value = "id") String id,
                                            @RequestParam(value = "paginas") Optional<Integer> paginas,
                                            @RequestParam(value = "quantidade") Optional<String> filtro ) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
         servicoFtp.buscaArquivosPaginados(usuario, new PageRequest(paginas.orElse(0), 5,
                Sort.Direction.ASC, filtro.orElse("id")));
    }


//    @GetMapping(value = "/download")
//    @ApiOperation(value = "Download arquivos")
//    public void downloadArquivo(@RequestBody ArquivoDownload arquivo) {
//        servicoUsuario.findById(arquivo.getNomeArquivo());
//    }


    @DeleteMapping(value = "/{id}")
    @ApiParam(name = "id", required = true)
    @ApiOperation(value = "Deleta arquivos do usuário")
    public void deletar(@PathVariable String id, @RequestParam String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
    }



}
