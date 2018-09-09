package com.daniel.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daniel.helpdesk.api.entity.Usuario;
import com.daniel.helpdesk.api.enums.ProfileEnum;
import com.daniel.helpdesk.api.repository.UsuarioRepository;

@SpringBootApplication
public class HelpDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(usuarioRepository, passwordEncoder);
		};
	}
	
	private void initUsers(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		Usuario admin = new Usuario();
		admin.setEmail("dd.justina@hotmail.com");
		admin.setSenha(passwordEncoder.encode("leinad"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		Usuario find = usuarioRepository.findByEmail("dd.justina@hotmail.com");
		if(find == null) {
			usuarioRepository.save(admin);
		}
	}
}
