package com.daniel.helpdesk.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daniel.helpdesk.api.entity.User;
import com.daniel.helpdesk.api.security.jwt.JwtUserFactory;
import com.daniel.helpdesk.api.service.UsuarioService;

@Service
public class JwtUserDetailsImp implements UserDetailsService{

	@Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    		User user = usuarioService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("NÃ£o foram encontrados usuarios com esse username '%s'.", email));
        } else {
            return JwtUserFactory.create(user);
        }
    }
	
}

