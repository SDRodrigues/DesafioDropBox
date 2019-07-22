package com.desafioftp.desafio.usuario;

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
        this.setId(usuario.getId());
        this.setNome(usuario.getNome());
        this.setIdade(usuario.getIdade());
        this.setProfissao(usuario.getProfissao());
        this.setSenha(usuario.getSenha());
        this.setArquivos(usuario.getArquivos());
    }

    public Usuario upload(){
        return new Usuario(id, nome, idade, profissao, senha, arquivos);
    }
}
