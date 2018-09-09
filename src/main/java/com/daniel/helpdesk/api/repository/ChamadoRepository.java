package com.daniel.helpdesk.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.daniel.helpdesk.api.entity.Chamado;

public interface ChamadoRepository extends MongoRepository<Chamado, String>{

	
	Page<Chamado> findByUsuarioIdOrderByDataDesc(Pageable pages, String usuarioId);
	
	Page<Chamado> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDataDesc(
			String titulo, String status, String prioridade, Pageable pages);
			
	Page<Chamado> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDataDesc(
			String titulo, String status, String prioridade, Pageable pages);
	
	
	Page<Chamado> findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioAtribuidoIdOrderByDataDesc(
			String titulo, String status, String prioridade, Pageable pages);
	
	Page<Chamado> findByNumero(Integer numero,Pageable pages);
	
}
