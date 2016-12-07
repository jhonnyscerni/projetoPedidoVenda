package com.algaworks.pedidovenda.service;


import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	UsuarioRepository repository;
	@Inject
	private UsuarioRepository usuarios;

	

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarios.salvar(usuario);
	}

	

}