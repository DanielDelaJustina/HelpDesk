package com.daniel.helpdesk.api.repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
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
