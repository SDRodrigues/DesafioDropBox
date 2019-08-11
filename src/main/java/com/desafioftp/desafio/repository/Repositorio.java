package com.desafioftp.desafio.repository;

import com.desafioftp.desafio.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Repositorio extends MongoRepository<Usuario, Integer> {

    Optional<Usuario> findById(String id);

    void deleteById(String id);
}
