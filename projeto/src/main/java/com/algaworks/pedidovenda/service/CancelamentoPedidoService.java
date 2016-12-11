package com.algaworks.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.model.StatusPedido;
import com.algaworks.pedidovenda.repository.PedidoRepository;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PedidoRepository pedidos;
	
	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido cancelar(Pedido pedido) throws NegocioException {
		// TODO Auto-generated method stub
		pedido = this.pedidos.porId(pedido.getId());
		if (pedido.isNaoCancelavel()) {
			throw new NegocioException("Pedido nao pode ser Cancelado no Status"
										+ pedido.getStatus().getDescricao());
		}
		
		if (pedido.isEmitido()) {
			this.estoqueService.retornarItensEstoque(pedido);
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}

}
