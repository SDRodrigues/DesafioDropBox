package com.desafioftp.desafio.service;
import com.desafioftp.desafio.exception.ObjetoNaoEncontrado;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServicoUsuario {

    private  Repositorio repositorio;

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

    public Optional<Usuario> lerUsuarioId(Integer id) {
       return repositorio.findById(id);
    }

    public Usuario editaUsuario(Integer id, Usuario usuario) {
        usuario.setId(id);
        return this.repositorio.save(usuario);
    }

    public void deletaUsuarioId(Integer id) {
        this.repositorio.deleteById(id);
    }

}
