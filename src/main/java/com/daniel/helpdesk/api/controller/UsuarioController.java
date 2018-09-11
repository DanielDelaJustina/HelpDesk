package com.daniel.helpdesk.api.controller;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.helpdesk.api.entity.User;
import com.daniel.helpdesk.api.response.Response;
import com.daniel.helpdesk.api.service.UsuarioService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins="*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<User>();
		try {
			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) usuarioService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (DuplicateKeyException dE) {
			response.getErrors().add("E-mail já registrado!");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult result) {
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "E-mail não informado"));
			return;
		}
	}
	
	@PutMapping()
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<User>();
		try {
			validateUpdate(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) usuarioService.createOrUpdate(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateUpdate(User user, BindingResult result) {
		if (user.getId() == null) {
			result.addError(new ObjectError("User", "Id não informado"));
			return;
		}
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "E-mail não informado"));
			return;
		}
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<User>> findById(@PathVariable("id") String id) {
		Response<User> response = new Response<User>();
		Optional<User> userOptional = usuarioService.findById(id);
		User user = userOptional.get();
		if (user == null) {
			response.getErrors().add("Não foi encontrado usuarios registrado com esse id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(user);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Optional<User> userOptional = usuarioService.findById(id);
		User user = userOptional.get();
		if (user == null) {
			response.getErrors().add("Não foi encontrado usuarios registrado com esse id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		usuarioService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('ADMIN')")
    public  ResponseEntity<Response<Page<User>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<User>> response = new Response<Page<User>>();
		Page<User> users = usuarioService.findAll(page, count);
		response.setData(users);
		return ResponseEntity.ok(response);
    }
}
