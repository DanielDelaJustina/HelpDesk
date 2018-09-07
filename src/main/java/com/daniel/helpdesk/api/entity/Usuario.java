package com.daniel.helpdesk.api.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.daniel.helpdesk.api.enums.ProfileEnum;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Usuario {

	@Id
	private String id;
	
	@Indexed(unique = true)
	@NotBlank(message = "Email Obrigatório")
	@Email(message = "Email Inválido" )
	private String email;
	
	@NotBlank(message = "Senha Obrigatório")
	@Size(min=6)
	private String senha;
	
	private ProfileEnum profile;
	
}
