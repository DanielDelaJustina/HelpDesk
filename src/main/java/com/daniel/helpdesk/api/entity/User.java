package com.daniel.helpdesk.api.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.daniel.helpdesk.api.enums.ProfileEnum;

@Document
public class User {

	@Id
	private String id;
	
	@Indexed(unique = true)
	@NotBlank(message = "Email Obrigatório")
	@Email(message = "Email Inválido" )
	private String email;
	
	@NotBlank(message = "Senha Obrigatório")
	@Size(min=6)
	private String password;
	
	private ProfileEnum profile;

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public ProfileEnum getProfile() {
		return profile;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}	
	
}
