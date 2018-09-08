package com.daniel.helpdesk.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daniel.helpdesk.api.entity.Usuario;

public interface UsuarioService {

	
	Usuario findByEmail(String email);
	
	Usuario createOrUpdate(Usuario usuario);
	
	Optional<Usuario> findById(String id);
	
	void delete(String id);
	
	Page<Usuario> findAll(int page, int count);
}
