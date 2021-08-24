package com.jmarcostech.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer cod;
	private String status;
	
	private EstadoPagamento() {
		
	}

	private EstadoPagamento(Integer cod, String status) {
		this.cod = cod;
		this.status = status;
	}

	public Integer getCod() {
		return cod;
	}

	public String getStatus() {
		return status;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(EstadoPagamento x: EstadoPagamento.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+ cod);
	}
	
	
}
