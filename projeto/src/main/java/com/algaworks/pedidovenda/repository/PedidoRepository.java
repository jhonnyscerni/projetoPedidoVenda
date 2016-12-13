package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Pedido;
import com.algaworks.pedidovenda.repository.filter.PedidoFilter;

public class PedidoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	private Criteria criarCriteriaParaFiltro(PedidoFilter filtro){
Session session = this.manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Pedido.class)
				// fazemos uma associação (join) com cliente e nomeamos como "c"
				.createAlias("cliente", "cliente")
				// fazemos uma associação (join) com vendedor e nomeamos como "v"
				.createAlias("vendedor", "v");
		
		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equals) a filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal) a filtro.numeroDe
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		
		if (filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeCliente())) {
			// acessamos o nome do cliente associado ao pedido pelo alias "c", criado anteriormente
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			// acessamos o nome do vendedor associado ao pedido pelo alias "v", criado anteriormente
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		
		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum StatusPedido
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}
		
		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> filtrados(PedidoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistro());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		}else if(filtro.getPropriedadeOrdenacao() != null){
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}

	public Pedido guardar(Pedido pedido) {
		return this.manager.merge(pedido);
	}

	public Pedido porId(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Pedido.class, id);
	}

	public int quantidadeFiltrados(PedidoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		return ((Number)criteria.uniqueResult()).intValue();
	}
	
}
