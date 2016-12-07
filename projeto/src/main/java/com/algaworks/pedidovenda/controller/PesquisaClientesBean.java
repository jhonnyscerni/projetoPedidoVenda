package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.model.TipoPessoa;
import com.algaworks.pedidovenda.repository.ClienteRepository;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteRepository clientes;

    private ClienteFilter filtro;
    private List<Cliente> clientesFiltrados;
    
    private Cliente clienteSelecionado;


    public PesquisaClientesBean() {
        filtro = new ClienteFilter();
        clientesFiltrados = new ArrayList<>();
    }
    
    @PostConstruct public void init()
	{ System.out.println("Inicializando..."); 
	this.setClientesFiltrados(clientes.filtrados(filtro)); 
	}

    public TipoPessoa[] getTipos(){
		return TipoPessoa.values();
	}
    
    public void pesquisar() {
        clientesFiltrados = clientes.filtrados(filtro);
    }

    public List<Cliente> getClientesFiltrados() {
        return clientesFiltrados;
    }

    public ClienteFilter getFiltro() {
        return filtro;
    }
       
	public void setClientesFiltrados(List<Cliente> clientesFiltrados) {
		this.clientesFiltrados = clientesFiltrados;
	}

	public void setFiltro(ClienteFilter filtro) {
		this.filtro = filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	
	public void excluir() {
		clientes.remover(clienteSelecionado);
		clientesFiltrados.remove(clienteSelecionado);
		
		FacesUtil.addInfoMessage("Cliente " + clienteSelecionado.getNome() 
				+ " excluído com sucesso.");
	}
	
	
	
    
	

}