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
        return this.repositorio.save(usuario);
    }

    public List<Usuario> lerUsuario() {
        return this.repositorio.findAll();
    }

    public void deletaUsuarioId(Integer id) {
        this.repositorio.deleteById(id);
    }

    public Usuario editaUsuario(Integer id, Usuario usuario) {
        usuario.setId(id);
    return this.repositorio.save(usuario);
    }

    public Optional<Usuario> lerUsuarioId(Integer id) {
        return this.repositorio.findById(id);
    }


}
