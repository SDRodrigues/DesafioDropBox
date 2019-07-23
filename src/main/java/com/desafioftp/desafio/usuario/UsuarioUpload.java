package com.desafioftp.desafio.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UsuarioUpload {
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;
    private List<Long> arquivos;

    public UsuarioUpload(@NotNull Usuario usuario) {
        this.setId(usuario.getId());
        this.setNome(usuario.getNome());
        this.setIdade(usuario.getIdade());
        this.setProfissao(usuario.getProfissao());
        this.setSenha(usuario.getSenha());
        this.setArquivos(usuario.getArquivos());
    }
}
