package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.UsuarioRepository;
import com.algaworks.pedidovenda.repository.filter.UsuarioFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarios;

	private UsuarioFilter filtro;
	private List<Usuario> usuariosFiltrados;

	private Usuario usuarioSelecionado;

	public PesquisaUsuariosBean() {
		filtro = new UsuarioFilter();
		usuariosFiltrados = new ArrayList<>();
	}

	@PostConstruct
	public void init() {
		System.out.println("Inicializando...");
		this.setUsuariosFiltrados(usuarios.filtrados(filtro));
	}

	public void excluir() {
		try {
			usuarios.remover(usuarioSelecionado);
			usuariosFiltrados.remove(usuarioSelecionado);

			FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			FacesUtil.addErrorMessage(e.getMessage());
		}

	}

	public void pesquisar() {
		usuariosFiltrados = usuarios.filtrados(filtro);
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
		this.usuariosFiltrados = usuariosFiltrados;
	}

}