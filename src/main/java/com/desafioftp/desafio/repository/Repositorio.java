package com.desafioftp.desafio.repository;

import com.desafioftp.desafio.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Repositorio extends MongoRepository<Usuario, Integer> {
}
