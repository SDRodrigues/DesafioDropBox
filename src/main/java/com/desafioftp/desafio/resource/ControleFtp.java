package com.desafioftp.desafio.resource;

import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.service.ServicoFtp;
import com.desafioftp.desafio.service.ServicoUsuario;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("v1/arquivos")
public class ControleFtp {


     private ServicoUsuario servicoUsuario;
     private ServicoFtp servicoFtp;

    @Autowired
    public ControleFtp(ServicoUsuario servicoUsuario, ServicoFtp servicoFtp) {
        this.servicoUsuario = servicoUsuario;
        this.servicoFtp = servicoFtp;
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    @ApiOperation(value="Envia arquivos", response= Usuario.class, notes="Essa operação envia os arquivos" +
            " do usuário.")
    @ApiResponses(value= {
            @ApiResponse(code=201, message="Enviou arquivos com sucesso", response= Usuario.class),
            @ApiResponse(code=404, message = "Não encontrou arquivos", response = Usuario.class),
            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
    })
    public void upload(@PathVariable(value = "id") Integer id, @RequestParam MultipartFile arquivo)  {
        Optional<Usuario> usuarioAux = servicoUsuario.lerUsuarioId(id);
        Usuario usuario = usuarioAux.get();

        this.servicoFtp.enviaArquivo(arquivo,usuario);
    }

//    @GetMapping()
//    @ResponseBody
//    @ApiOperation(value="Buscar usuário", response= Usuario.class, notes="Essa operação busca o usuario.")
//    @ApiResponses(value= {
//            @ApiResponse(code=200, message="Retorna um Usuario com uma mensagem de sucesso", response= Usuario.class),
//            @ApiResponse(code=404, message = "Não encontrou usuário", response = Usuario.class),
//            @ApiResponse(code=500, message="Erro interno", response= Usuario.class)
//    })
//    public boolean listar(String arquivo){
//        return this.servicoUpload.listarArquivos();
//    }
//
//    @DeleteMapping(value = "/{id}")
//    @ApiOperation(value="Excluir usuário", response= Usuario.class, notes="Essa operação exclui o model.")
//    @ApiResponses(value= {
//            @ApiResponse(code=200, message="Excluiu o model", response=Usuario.class),
//            @ApiResponse(code=500, message="Erro interno", response=Usuario.class)
//    })
//    public void deletaArquivo(@PathVariable @NotNull String arquivo, UsuarioDto usuario) {
//        servicoUpload.excluirArquivos(arquivo, usuario); }
}
