package com.desafioftp.desafio.model;

import lombok.*;
import java.util.List;

@Data
public class UsuarioDto {
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;
    private List<Arquivos> arquivos;

    public UsuarioDto() {
        Usuario usuario = new Usuario();
        this.setNome(usuario.getNome());
        this.setIdade(usuario.getIdade());
        this.setSenha(usuario.getSenha());
    }
}
