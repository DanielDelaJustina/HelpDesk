package com.daniel.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.daniel.helpdesk.api.entity.MudancaStatus;

public interface MudancaStatusRepository extends MongoRepository<MudancaStatus, String>{

	Iterable<MudancaStatus> findByChamadoIdOrderByDataMudancaStatusDesc(String chamadoId);
	
	
}
