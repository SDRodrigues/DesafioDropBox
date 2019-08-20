package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.*;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void uploadArquivo(@PathVariable String id, @RequestBody MultipartFile arquivo)  {
        this.servicoFtp.salvaArquivo(id, arquivo);
    }


    @GetMapping(value = "/{id}/{paginas}/{quantidade}")
    @ApiOperation(value="Busca arquivos com filtros do usuario")
    public Page<FTPFile> arquivosPaginados(@PathVariable String id,
                                            @PathVariable Integer paginas,
                                            @PathVariable Integer quantidade ) {
         return servicoFtp.listaArquivosPaginados(id, paginas, quantidade);
    }

    @GetMapping(value = "/compartilha/{idUsuarioEnvia}/{arquivo}/{idUsuarioRecebe}")
    @ApiOperation(value="compartilha arquivos")
    public void compartilhaArquivos(@PathVariable String idUsuarioEnvia,
                                             @PathVariable String arquivo,
                                             @PathVariable String idUsuarioRecebe
                                             ) {
         servicoFtp.arquivosCompartilhados(idUsuarioEnvia, idUsuarioRecebe, arquivo);
    }


    @DeleteMapping(value = "/{id}")
    @ApiParam(name = "id", required = true)
    @ApiOperation(value = "Deleta arquivos do usuário")
    public void deletar(@PathVariable String id, @RequestParam String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.findById(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
    }


}
