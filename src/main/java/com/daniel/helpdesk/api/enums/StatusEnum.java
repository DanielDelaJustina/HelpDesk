package com.daniel.helpdesk.api.enums;

import lombok.Getter;

public enum StatusEnum {
	
	NOVO("Novo"),
	DESIGNADO("Designado"),
	RESOLVIDO("Resolvido"),
	APROVADO("Aprovado"),
	REPROVADO("Reprovado"),
	FECHAR("Fechar");
	
	private StatusEnum(String status){
        this.status=status;
    }
	
	@Getter private String status;

}
