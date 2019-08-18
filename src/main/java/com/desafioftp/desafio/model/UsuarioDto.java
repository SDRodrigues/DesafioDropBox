package com.desafioftp.desafio.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String id;
    private String nome;
    private Integer idade;
    private String profissao;

    public UsuarioDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        idade = usuario.getIdade();
        profissao = usuario.getProfissao();
    }

    public static Usuario dtoParaUsuario(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.getId(),
                usuarioDto.getNome(),
                usuarioDto.getIdade(),
                usuarioDto.getProfissao()
        );
    }

}
