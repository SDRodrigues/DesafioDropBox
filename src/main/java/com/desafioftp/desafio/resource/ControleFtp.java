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
    public FTPFile[] listaUpload(@PathVariable String id ) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        return this.servicoFtp.listaUpload(usuario);
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
                                            @RequestParam Integer filtro ) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
         return servicoFtp.buscaArquivosPaginados(usuario, paginas, filtro);
    }


    @ApiOperation(value = "download dos arquivos")
    @GetMapping(value = "/downloads/{id}")
    public void downloadArquivo(
            @ApiParam @PathVariable String id,
            @ApiParam @RequestParam String arquivo ){
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        this.servicoFtp.downloadArquivo(arquivo,usuario);
    }


    @DeleteMapping(value = "/{id}")
    @ApiParam(name = "id", required = true)
    @ApiOperation(value = "Deleta arquivos do usuário")
    public void deletar(@PathVariable String id, @RequestParam String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
    }



}
