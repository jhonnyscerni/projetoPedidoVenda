package com.algaworks.pedidovenda.repository.filter;

import java.io.Serializable;

import com.algaworks.pedidovenda.model.TipoPessoa;


public class ClienteFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String email;
	
	private TipoPessoa[] tipo;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoPessoa[] getTipo() {
		return tipo;
	}

	public void setTipo(TipoPessoa[] tipo) {
		this.tipo = tipo;
	}

	

}
