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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Date getDataMudancaStatus() {
		return dataMudancaStatus;
	}

	public void setDataMudancaStatus(Date dataMudancaStatus) {
		this.dataMudancaStatus = dataMudancaStatus;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	
	
}
