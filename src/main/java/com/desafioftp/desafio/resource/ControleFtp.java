package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Arquivos;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.*;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ControleFtp( ServicoFtp servicoFtp, ServicoUsuario servicoUsuario) {
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
    public void storeFile(@PathVariable(value = "id") String id, @RequestParam MultipartFile arquivo)  {
        this.servicoFtp.storeFile(servicoUsuario.lerUsuarioId(id).get().getId(), arquivo);
    }

//    @GetMapping(value = "/{id}")
//    @ApiOperation(value="Busca Arquivos do usuário")
//    public ArrayList<Arquivos> buscaArquivos(@PathVariable(value = "id") Integer id )  {
//        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
//        return this.servicoFtp.buscaArquivos(usuario);
//    }

        @GetMapping(value = "/{id}")
    @ApiOperation(value="Busca Arquivos do usuário", response= Usuario.class, notes="Essa operação busca os arquivos" +
            " do usuário.")
    public FTPFile[] listaArquivos(@PathVariable(value = "id") String id ) {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        return this.servicoFtp.buscaArquivosDoUsuario(usuario);
    }

    @GetMapping(value = "/paginas/{id}")
    @ApiOperation(value="Busca arquivos com filtros do usuario", response= Usuario.class,
            notes="Essa operação busca os arquivos paginados do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso", response= Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
    })
    public Page<Arquivos> arquivosPaginados(@PathVariable(value = "id") String id,
                                            @RequestParam(value = "paginas") Integer paginas,
                                            @RequestParam(value = "quantidade") Integer quantidade )  {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        return servicoFtp.buscaArquivosPaginados(usuario, new PageRequest(paginas, quantidade));
    }




    @DeleteMapping(value = "/{id}")
    @ApiParam(name = "id", required = true)
    @ApiOperation(value = "Deleta arquivos do usuário")
    public void deleteFile(@PathVariable String id, @RequestBody String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        servicoFtp.excluirArquivos(usuario.get().getId(), nomeArquivo);
    }

}
