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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<ArrayList<Arquivos>> buscaArquivos(@PathVariable(value = "id") Integer id )  {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        return ResponseEntity.ok().body(this.servicoFtp.buscaArquivos(usuario));
    }

    @GetMapping(value = "/usuarios/{id}/pagina/{paginas}/arquivos/{quantidade}")
    @ApiOperation(value="Busca arquivos paginados do usuario", response= Usuario.class,
            notes="Essa operação busca os arquivos paginados do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Buscou arquivos com sucesso", response= Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
    })
    public Page<Arquivos> arquivosPaginados(@PathVariable(value = "id") Integer id,
                                            @PathVariable Integer paginas,
                                            @PathVariable Integer quantidade )  {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        return servicoFtp.buscaArquivosPaginados(usuario, quantidade, paginas);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deleta arquivos do usuário")
    public ResponseEntity<Void> deleteFile(@PathVariable Integer id, @RequestBody String nomeArquivo) {
        Optional<Usuario> usuario = servicoUsuario.lerUsuarioId(id);
        servicoFtp.excluirArquivos(usuario, nomeArquivo);
        return ResponseEntity.noContent().build();
    }

}
