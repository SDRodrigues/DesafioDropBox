package com.desafioftp.desafio.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UsuarioDto {
    private String id;
    private String nome;
    private Integer idade;
    private String profissao;
    private String senha;
    private List<MultipartFile> arquivos;

    public UsuarioDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        idade = usuario.getIdade();
        profissao = usuario.getProfissao();
        arquivos = usuario.getFiles();
    }

//    public Usuario dtoParaUsuario(UsuarioDto usuarioDto) {
//        return new Usuario(usuarioDto.getId(),
//                usuarioDto.getNome(),
//                usuarioDto.getIdade(),
//                usuarioDto.getProfissao(),
//                usuarioDto.getSenha(),
//                usuarioDto.getArquivos());
//    }

}
