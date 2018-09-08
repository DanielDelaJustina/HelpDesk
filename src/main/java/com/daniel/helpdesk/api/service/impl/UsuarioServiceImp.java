package com.daniel.helpdesk.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daniel.helpdesk.api.entity.Usuario;
import com.daniel.helpdesk.api.repository.UsuarioRepository;
import com.daniel.helpdesk.api.service.UsuarioService;


@Service
public class UsuarioServiceImp implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Override
	public Usuario findByEmail(String email) {
		return this.usuarioRepository.findByEmail(email);
	}

	@Override
	public Usuario createOrUpdate(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> findById(String id) {
		return this.usuarioRepository.findById(id);
	}

	@Override
	public void delete(String id) {
		this.usuarioRepository.deleteById(id);		
	}

	@Override
	public Page<Usuario> findAll(int page, int count) {
		
		PageRequest pageRequest =  PageRequest.of(page,count);
		return usuarioRepository.findAll(pageRequest);
	}

}
