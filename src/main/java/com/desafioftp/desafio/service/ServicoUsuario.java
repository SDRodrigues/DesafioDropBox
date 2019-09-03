package com.desafioftp.desafio.service;

import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.model.UsuarioDto;
import com.desafioftp.desafio.repository.Repositorio;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ServicoUsuario {

    private Repositorio repositorio;
    private static final String NOTFOUND = "Usuario não encontrado";

    @Autowired
    public ServicoUsuario(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return this.repositorio.insert(usuario);
    }

    public List<Usuario> lerUsuario() {
        return this.repositorio.findAll();
    }

    public Optional<Usuario> findById(String id) {
        Optional<Usuario> usuario = repositorio.findById(id);
        if (usuario.isEmpty()) {
            throw new ObjetoNaoEncontrado(NOTFOUND);
        }
        return usuario;
    }

    public void deletaUsuarioId(String id) {
        this.findById(id);
        this.repositorio.deleteById(id);
    }

    public Usuario editaUsuario(Usuario usuario) {
        Usuario novoUsuario = atualizandoUsuario(usuario.getId());
        atualizaUsuario(novoUsuario, usuario);
        return this.repositorio.save(novoUsuario);
    }

    private Usuario atualizandoUsuario(String id) {
        Optional<Usuario> usuario = this.repositorio.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new ObjetoNaoEncontrado(NOTFOUND);
        }
    }

    private void atualizaUsuario(Usuario novoUsuario, Usuario usuario) {
        novoUsuario.setId(usuario.getId());
        novoUsuario.setNome(usuario.getNome());
        novoUsuario.setIdade(usuario.getIdade());
        novoUsuario.setProfissao(usuario.getProfissao());
    }

    public Usuario dtoParaUsuario(UsuarioDto usuarioDto) {
        return new Usuario(
                usuarioDto.getId(),
                usuarioDto.getNome(),
                usuarioDto.getIdade(),
                usuarioDto.getProfissao()
        );
    }
}