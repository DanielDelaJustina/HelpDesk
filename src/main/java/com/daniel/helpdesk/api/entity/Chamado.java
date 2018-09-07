package com.daniel.helpdesk.api.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.daniel.helpdesk.api.enums.PrioridadeEnum;
import com.daniel.helpdesk.api.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class Chamado {
	
	@Id
	private String id;
	
	@DBRef(lazy = true)
	private Usuario usuario;
	
	private Date data;
	
	private String titulo;
	
	private Integer numero;
	
	private StatusEnum status;
	
	private PrioridadeEnum prioridade;
	
	@DBRef(lazy = true)
	private Usuario usuarioAtribuido;

	private String descricao;
	
	private String imagem;
	
	private List<MudancaStatus> alteracoes;
}
