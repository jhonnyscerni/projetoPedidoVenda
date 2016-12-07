package com.algaworks.pedidovenda.model;

public enum TipoPessoa {

	FISICA("Fisica"), JURIDICA("Juridica");
	
	private String descricao;

	TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
}
