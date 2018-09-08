package com.daniel.helpdesk.api.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.daniel.helpdesk.api.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class MudancaStatus {

	
	@Id
	private String id;
	
	@DBRef
	private Chamado chamado;

	@DBRef
	private Usuario usuarioAlteracao;
	
	private Date dataMudancaStatus;
	
	private StatusEnum status;
}
