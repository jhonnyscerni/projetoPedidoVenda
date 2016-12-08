package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.repository.GrupoRepository;
import com.algaworks.pedidovenda.service.CadastroUsuarioService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Inject
	private GrupoRepository grupos;

	private Grupo grupoSelecionado;
	
	private List<Grupo> grupoRaizes;
	
	@PostConstruct
	public void init(){
		grupoRaizes = grupos.findAll();
		adicionarGrupo();
		limpar();
	}
	
	private void limpar() {
		usuario = new Usuario();
		grupoSelecionado = null;
	}
	
	public void adicionarGrupo() {
		if (this.existeGrupoNoUsuario(this.grupoSelecionado)) {
			FacesUtil.addErrorMessage("Já existe um item no pedido com o produto informado.");
		}else{
			this.usuario.getGrupos().add(grupoSelecionado);
		}
	}
	
	private boolean existeGrupoNoUsuario(Grupo grupoSelecionado) {
		boolean existeGrupo = false;
		
		for (Grupo grupo : this.getUsuario().getGrupos()) {
			if (grupoSelecionado.getId().equals(grupo.getId())) {
				existeGrupo = true;
				break;
			}
		}
		return existeGrupo;
	}
	
	public void removerGrupo() {
		this.usuario.getGrupos().remove(grupoSelecionado);
	}
	
	public CadastroUsuarioBean() {
		
		usuario = new Usuario();
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void salvar(){
		this.usuario = cadastroUsuarioService.salvar(this.usuario);
		FacesUtil.addInfoMessage("Usuario Cadastrado com Sucesso!");
	}
	
	public boolean isEditando(){
		return this.usuario.getId() != null;
	}


	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}


	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getGrupoRaizes() {
		return grupoRaizes;
	}

	public void setGrupoRaizes(List<Grupo> grupoRaizes) {
		this.grupoRaizes = grupoRaizes;
	}
	
	

	
}
