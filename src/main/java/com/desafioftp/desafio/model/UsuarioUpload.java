package com.desafioftp.desafio.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpload {
    private Integer id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;

    public Usuario upload() {
        Usuario usuario = new Usuario();
        this.setNome(usuario.getNome());
        this.setIdade(usuario.getIdade());
        this.setProfissao(usuario.getProfissao());
        this.setSenha(usuario.getSenha());
        return usuario;
    }
}
