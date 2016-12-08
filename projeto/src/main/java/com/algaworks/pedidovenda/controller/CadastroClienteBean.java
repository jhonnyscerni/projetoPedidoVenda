package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.Endereco;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.service.CadastroClienteService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cliente cliente;
	private Endereco endereco;

	@Inject
	private CadastroClienteService service;

	public CadastroClienteBean() {
		limpar();
	}

	@PostConstruct
	public void init() {
		this.cliente.setTipo(TipoPessoa.FISICA);
	}

	public void adicionarEndereco() {

		this.cliente.getEnderecos().add(endereco);
		this.endereco.setCliente(cliente);
		endereco = new Endereco();
		

	}
	
	public void removerEndereco() {
		cliente.getEnderecos().remove(endereco);
	}

	private void limpar() {
		cliente = new Cliente();
		endereco = new Endereco();
	}

	public TipoPessoa[] getTipos() {
		return TipoPessoa.values();
	}

	public void salvar() {
		cliente.setNome(cliente.getNome().toUpperCase());
		cliente.setEmail(cliente.getEmail().toLowerCase());
		cliente.setDocumentoReceitaFederal(
				cliente.getDocumentoReceitaFederal().replace(".", "").replace("-", "").replace("/", ""));
		this.cliente = service.salvar(this.cliente);
		FacesUtil.addInfoMessage("Pedido Salvo Com Sucesso!");
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
