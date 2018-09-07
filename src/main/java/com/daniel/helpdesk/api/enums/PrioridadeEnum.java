package com.daniel.helpdesk.api.enums;

import lombok.Getter;

public enum PrioridadeEnum {
	
	ALTO("Alto"),
	NORMAL("Normal"),
	BAIXO("Baixo");
	
	private PrioridadeEnum(String prioridade){
        this.prioridade=prioridade;
    }
	
	@Getter private String prioridade;

}
