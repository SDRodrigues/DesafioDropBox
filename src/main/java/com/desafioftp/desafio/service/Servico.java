package com.desafioftp.desafio.service;
import com.desafioftp.desafio.model.Usuario;
import com.desafioftp.desafio.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class Servico {

    private  Repositorio repositorio;

    @Autowired
    public Servico(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario criarUsuario(Usuario usuario) {
        return repositorio.save(usuario);
    }

    public List<Usuario> lerUsuario() {
        return repositorio.findAll();
    }

    public Optional<Usuario> lerUsuarioId(Integer id) {
        return repositorio.findById(id);
    }

    public void deletaUsuario(Usuario usuario) {
        repositorio.delete(usuario);
    }

    public void deletaUsuarioId(Integer id) {
        repositorio.deleteById(id);
    }

    public Usuario editaUsuario(Integer id, Usuario usuario) {
        usuario.setId(id);
        return repositorio.save(usuario);
    }


}
