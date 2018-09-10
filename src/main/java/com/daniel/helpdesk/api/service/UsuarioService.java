package com.daniel.helpdesk.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.daniel.helpdesk.api.entity.User;

@Component
public interface UsuarioService {

	
	User findByEmail(String email);
	
	User createOrUpdate(User usuario);
	
	Optional<User> findById(String id);
	
	void delete(String id);
	
	Page<User> findAll(int page, int count);
}
