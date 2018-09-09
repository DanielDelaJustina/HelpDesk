package com.daniel.helpdesk.api.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daniel.helpdesk.api.entity.Usuario;
import com.daniel.helpdesk.api.security.jwt.JwtUserFactory;
import com.daniel.helpdesk.api.service.UsuarioService;

@Service
public class JwtUserDetailsImp implements UserDetailsService{

	private UsuarioService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario user = userService.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(String.format("Usuário nao encontrado '%s'.", email));
		}else {
			return JwtUserFactory.create(user);
		}
	}
}
