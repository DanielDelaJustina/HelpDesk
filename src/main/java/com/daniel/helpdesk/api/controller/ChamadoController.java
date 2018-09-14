package com.daniel.helpdesk.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.daniel.helpdesk.api.entity.Chamado;
import com.daniel.helpdesk.api.entity.MudancaStatus;
import com.daniel.helpdesk.api.entity.User;
import com.daniel.helpdesk.api.enums.ProfileEnum;
import com.daniel.helpdesk.api.enums.StatusEnum;
import com.daniel.helpdesk.api.response.Response;
import com.daniel.helpdesk.api.security.jwt.JwtTokenUtil;
import com.daniel.helpdesk.api.service.ChamadoService;
import com.daniel.helpdesk.api.service.UsuarioService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins="*")
public class ChamadoController {

	@Autowired
	private ChamadoService ticketService;
	
	@Autowired
	protected JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	protected UsuarioService usuarioService;


	@PostMapping()
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Chamado>> createOrUpdate(HttpServletRequest request, @RequestBody Chamado chamado,
			BindingResult result) {
		Response<Chamado> response = new Response<Chamado>();
		try {
			validateCreateTicket(chamado, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			chamado.setStatus(StatusEnum.NOVO);
			chamado.setUsuario(userFromRequest(request));
			chamado.setData(new Date());
			chamado.setNumero(generateNumber());
			Chamado chamadoPersisted = (Chamado) ticketService.createOrUpdate(chamado);
			response.setData(chamadoPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateTicket(Chamado chamado, BindingResult result) {
		if (chamado.getTitulo()== null) {
			result.addError(new ObjectError("Chamado", "Titulo não informado"));
			return;
		}
	}
	
	public User userFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = jwtTokenUtil.getUserNameFromToken(token);
        return usuarioService.findByEmail(email);
    }
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
	
	@PutMapping()
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<Chamado>> update(HttpServletRequest request, @RequestBody Chamado chamado,
			BindingResult result) {
		Response<Chamado> response = new Response<Chamado>();
		try {
			validateUpdateChamado(chamado, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Optional<Chamado> chamadoCurrentOptional = ticketService.findById(chamado.getId());
			Chamado chamadoCurrent = chamadoCurrentOptional.get();
			chamado.setStatus(chamadoCurrent.getStatus());
			chamado.setUsuario(chamadoCurrent.getUsuario());
			chamado.setData(chamadoCurrent.getData());
			chamado.setNumero(chamadoCurrent.getNumero());
			if(chamadoCurrent.getUsuarioAtribuido() != null) {
				chamado.setUsuarioAtribuido(chamadoCurrent.getUsuarioAtribuido());
			}
			Chamado chamadoPersisted = (Chamado) ticketService.createOrUpdate(chamado);
			response.setData(chamadoPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdateChamado(Chamado chamado, BindingResult result) {
		if (chamado.getId() == null) {
			result.addError(new ObjectError("Chamado", "Id não informado"));
			return;
		}
		if (chamado.getTitulo() == null) {
			result.addError(new ObjectError("Chamado", "Título não informado"));
			return;
		}
	}
	

	@GetMapping(value = "{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	public ResponseEntity<Response<Chamado>> findById(@PathVariable("id") String id) {
		Response<Chamado> response = new Response<Chamado>();
		Optional<Chamado> ticketOptional = ticketService.findById(id);
		Chamado ticket = ticketOptional.get();
		if (ticket == null) {
			response.getErrors().add("Registro com esse id não encontrado:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		List<MudancaStatus> changes = new ArrayList<MudancaStatus>();
		Iterable<MudancaStatus> changesCurrent =  ticketService.listChangeStatus(ticket.getId());
		for (Iterator<MudancaStatus> iterator = changesCurrent.iterator(); iterator.hasNext();) {
			MudancaStatus changeStatus = iterator.next();
			changeStatus.setChamado(null);
			changes.add(changeStatus);
		}	
		ticket.setAlteracoes(changes);
		response.setData(ticket);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
		Response<String> response = new Response<String>();
		Optional<Chamado> ticketOptional = ticketService.findById(id);
		Chamado ticket = ticketOptional.get();
		if (ticket == null) {
			response.getErrors().add("Registro com esse id não encontrado:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		ticketService.delete(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	/*
	
	@GetMapping(value = "{page}/{count}")
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public  ResponseEntity<Response<Page<Ticket>>> findAll(HttpServletRequest request, @PathVariable int page, @PathVariable int count) {
		
		Response<Page<Chamado>> response = new Response<Page<Chamado>>();
		Page<Chamado> tickets = null;
		User userRequest = userFromRequest(request);
		if(userRequest.getProfile().equals(ProfileEnum.ROLE_TECNICAL)) {
			tickets = ticketService.listTicket(page, count);
		} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
			tickets = ticketService.findByCurrentUser(page, count, userRequest.getId());
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
    }
	
	@GetMapping(value = "{page}/{count}/{number}/{title}/{status}/{priority}/{assigned}")
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
    public  ResponseEntity<Response<Page<Chamado>>> findByParams(HttpServletRequest request, 
    		 							@PathVariable int page, 
    		 							@PathVariable int count,
    		 							@PathVariable Integer number,
    		 							@PathVariable String title,
    		 							@PathVariable String status,
    		 							@PathVariable String priority,
    		 							@PathVariable boolean assigned) {
		
		title = title.equals("uninformed") ? "" : title;
		status = status.equals("uninformed") ? "" : status;
		priority = priority.equals("uninformed") ? "" : priority;
		
		Response<Page<Chamado>> response = new Response<Page<Chamado>>();
		Page<Chamado> tickets = null;
		if(number > 0) {
			tickets = ticketService.findByNumber(page, count, number);
		} else {
			User userRequest = userFromRequest(request);
			if(userRequest.getProfile().equals(ProfileEnum.ROLE_TECNICAL)) {
				if(assigned) {
					tickets = ticketService.findByParametersAndAssignedUser(page, count, title, status, priority, userRequest.getId());
				} else {
					tickets = ticketService.findByParameters(page, count, title, status, priority);
				}
			} else if(userRequest.getProfile().equals(ProfileEnum.ROLE_CUSTOMER)) {
				tickets = ticketService.findByParametersAndCurrentUser(page, count, title, status, priority, userRequest.getId());
			}
		}
		response.setData(tickets);
		return ResponseEntity.ok(response);
    }
	
	@PutMapping(value = "/{id}/{status}")
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	public ResponseEntity<Response<Chamado>> changeStatus(
													@PathVariable("id") String id, 
													@PathVariable("status") String status, 
													HttpServletRequest request,  
													@RequestBody Chamado ticket,
													BindingResult result) {
		
		Response<Chamado> response = new Response<Chamado>();
		try {
			validateChangeStatus(id, status, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			Optional<Chamado> ticketCurrentOptional = ticketService.findById(id);
			Chamado ticketCurrent = ticketCurrentOptional.get();
			ticketCurrent.setStatus(StatusEnum.getStatus(status));
			if(status.equals("Assigned")) {
				ticketCurrent.setUsuarioAtribuido(userFromRequest(request));
			}
			Chamado ticketPersisted = (Chamado) ticketService.createOrUpdate(ticketCurrent);
			MudancaStatus changeStatus = new MudancaStatus();
			changeStatus.setUsuarioAlteracao(userFromRequest(request));
			changeStatus.setDataMudancaStatus(new Date());
			changeStatus.setStatus(StatusEnum.getStatus(status));
			changeStatus.setChamado(ticketPersisted);
			ticketService.createChangeStatus(changeStatus);
			response.setData(ticketPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}
	
	private void validateChangeStatus(String id,String status, BindingResult result) {
		if (id == null || id.equals("")) {
			result.addError(new ObjectError("Ticket", "Id no information"));
			return;
		}
		if (status == null || status.equals("")) {
			result.addError(new ObjectError("Ticket", "Status no information"));
			return;
		}
	}
	
	@GetMapping(value = "/summary")
	public ResponseEntity<Response<Summary>> findChart() {
		Response<Summary> response = new Response<Summary>();
		Summary chart = new Summary();
		int amountNew = 0;
		int amountResolved = 0;
		int amountApproved = 0;
		int amountDisapproved = 0;
		int amountAssigned = 0;
		int amountClosed = 0;
		Iterable<Ticket> tickets = ticketService.findAll();
		if (tickets != null) {
			for (Iterator<Ticket> iterator = tickets.iterator(); iterator.hasNext();) {
				Ticket ticket = iterator.next();
				if(ticket.getStatus().equals(StatusEnum.New)){
					amountNew ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Resolved)){
					amountResolved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Approved)){
					amountApproved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Disapproved)){
					amountDisapproved ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Assigned)){
					amountAssigned ++;
				}
				if(ticket.getStatus().equals(StatusEnum.Closed)){
					amountClosed ++;
				}
			}	
		}
		chart.setAmountNew(amountNew);
		chart.setAmountResolved(amountResolved);
		chart.setAmountApproved(amountApproved);
		chart.setAmountDisapproved(amountDisapproved);
		chart.setAmountAssigned(amountAssigned);
		chart.setAmountClosed(amountClosed);
		response.setData(chart);
		return ResponseEntity.ok(response);
	}
*/	
}
