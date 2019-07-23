package com.desafioftp.desafio.model;

import lombok.Data;
import java.util.List;

@Data
public class UsuarioUpload {
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;
    private List<Long> arquivos;

    public UsuarioUpload(Usuario usuario) {
        this.setNome(usuario.getNome());
        this.setIdade(usuario.getIdade());
        this.setProfissao(usuario.getProfissao());
        this.setSenha(usuario.getSenha());
    }
}
