package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.ClienteRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente){
		return clientes.salvar(cliente);
	}

}
