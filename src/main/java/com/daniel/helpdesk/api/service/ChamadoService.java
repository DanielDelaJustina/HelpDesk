package com.daniel.helpdesk.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.daniel.helpdesk.api.entity.MudancaStatus;
import com.daniel.helpdesk.api.entity.Chamado;

@Component
public interface ChamadoService {

	Chamado createOrUpdate(Chamado chamado);
	
	Optional<Chamado> findById(String id);
	
	void delete(String id);
	
	Page<Chamado> listTicket(int page, int count);
	
	MudancaStatus createChangeStatus(MudancaStatus mudancaStatus);
	
	Iterable<MudancaStatus> listChangeStatus(String chamadoId);
	
	Page<Chamado> findByCurrentUser(int page, int count, String userId);
	
	Page<Chamado> findByParameters(int page, int count,String title,String status,String priority);
	
	Page<Chamado> findByParametersAndCurrentUser(int page, int count,String title,String status,String priority,String userId);
	
	Page<Chamado> findByNumber(int page, int count,Integer number);
	
	Iterable<Chamado> findAll();
	
	public Page<Chamado> findByParametersAndAssignedUser(int page, int count,String title,String status,String priority,String assignedUserId);
	
}