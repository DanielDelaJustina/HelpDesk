package com.daniel.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.daniel.helpdesk.api.entity.User;

public interface UsuarioRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
}
