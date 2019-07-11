package com.desafioftp.desafio.repository;

import com.desafioftp.desafio.usuario.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Repositorio extends MongoRepository<Usuario, Integer> {
}
