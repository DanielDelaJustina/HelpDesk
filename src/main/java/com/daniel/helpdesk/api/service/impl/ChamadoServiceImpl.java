package com.daniel.helpdesk.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daniel.helpdesk.api.entity.Chamado;
import com.daniel.helpdesk.api.entity.MudancaStatus;
import com.daniel.helpdesk.api.repository.ChamadoRepository;
import com.daniel.helpdesk.api.repository.MudancaStatusRepository;
import com.daniel.helpdesk.api.service.ChamadoService;

@Service
public class ChamadoServiceImpl implements ChamadoService{

	@Autowired
	private ChamadoRepository chamadoRepository;
	
	@Autowired
	private MudancaStatusRepository mandancaStatusRepository;
	
	@Override
	public Chamado createOrUpdate(Chamado chamado) {
		return this.chamadoRepository.save(chamado);
	}

	@Override
	public Optional<Chamado> findById(String id) {
		return this.chamadoRepository.findById(id);
	}

	@Override
	public void delete(String id) {
		this.chamadoRepository.deleteById(id);		
	}

	@Override
	public Page<Chamado> listTicket(int page, int count) {
		Pageable pages = PageRequest.of(page, count);
		return this.chamadoRepository.findAll(pages);
	}

	@Override
	public MudancaStatus createChangeStatus(MudancaStatus mudancaStatus) {
		return this.mandancaStatusRepository.save(mudancaStatus) ;
	}

	@Override
	public Iterable<MudancaStatus> listChangeStatus(String chamadoId) {
		return this.mandancaStatusRepository.findByChamadoIdOrderByDataMudancaStatusDesc(chamadoId) ;
	}

	@Override
	public Page<Chamado> findByCurrentUser(int page, int count, String userId) {
		Pageable pages = PageRequest.of(page, count);
		return this.chamadoRepository.findByUsuarioIdOrderByDataDesc(pages, userId);
	}

	@Override
	public Page<Chamado> findByParameters(int page, int count, String title, String status, String priority) {
		Pageable pages = PageRequest.of(page, count);
		return this.chamadoRepository.
				findByTituloIgnoreCaseContainingAndStatusAndPrioridadeOrderByDataDesc(title, status, priority, pages);
	}

	@Override
	public Page<Chamado> findByParametersAndCurrentUser(int page, int count, String title, String status,
			String priority, String userId) {
		Pageable pages = PageRequest.of(page, count);
		return this.chamadoRepository.
				findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioIdOrderByDataDesc(title, status, priority, pages);
	}

	@Override
	public Page<Chamado> findByNumber(int page, int count, Integer number) {
		Pageable pages = PageRequest.of(page, count);
		return this.chamadoRepository.findByNumero(number, pages);
	}

	@Override
	public Iterable<Chamado> findAll() {
		return this.chamadoRepository.findAll();
	}

	@Override
	public Page<Chamado> findByParametersAndAssignedUser(int page, int count, String title, String status,
			String priority, String assignedUserId) {
		
		Pageable pages = PageRequest.of(page, count);		
		return this.chamadoRepository.findByTituloIgnoreCaseContainingAndStatusAndPrioridadeAndUsuarioAtribuidoIdOrderByDataDesc(title, status, priority, pages);
	}

}
