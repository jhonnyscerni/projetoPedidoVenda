package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PedidoRepository pedidos;
	
	@Transactional
	public Pedido salvar(Pedido pedido) throws NegocioException{
		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}
		pedido.recalcularValorTotal();
		
		if (pedido.isNaoAlteravel()) {
			throw new NegocioException("Pedido nao pode ser alterado no status"+ pedido.getStatus().getDescricao());
		}
		
		if (pedido.getItens().isEmpty()) {
			throw new NegocioException("O pedido deve possuir pelo menos um item");
		}
		if(pedido.isValorTotalNegativo())
		{
			throw new NegocioException("Valor Total do Pedido nao pode ser negativo");
		}
		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}

}
