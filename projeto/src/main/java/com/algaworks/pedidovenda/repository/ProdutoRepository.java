package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Categoria;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.filter.ProdutoFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class ProdutoRepository implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Produto salvar(Produto produto) {
		return manager.merge(produto);
	}

	@Transactional
	public void remover(Produto produto) throws NegocioException {
		try {
			produto = porId(produto.getId());
			manager.remove(produto);
			manager.flush();
		} catch (PersistenceException e) {
			// TODO: handle exception
			throw new NegocioException("Produto não pode ser excluido");
		}

	}

	public List<Produto> filtrados(ProdutoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
		Fetch<Produto, Categoria> categoriaJoin = produtoRoot.fetch("categoria", JoinType.INNER);
		categoriaJoin.fetch("categoriaPai", JoinType.INNER);
		
		if (StringUtils.isNotBlank(filtro.getSku())) {
			predicates.add(builder.equal(produtoRoot.get("sku"), filtro.getSku()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(produtoRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(produtoRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(produtoRoot.get("nome")));
		
		TypedQuery<Produto> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	

	/*
	 * @SuppressWarnings("unchecked") public List<Produto>
	 * filtrados(ProdutoFilter filter){ Session session =
	 * manager.unwrap(Session.class); Criteria criteria =
	 * session.createCriteria(Produto.class);
	 * 
	 * if (StringUtils.isNotBlank(filter.getSku())) {
	 * criteria.add(Restrictions.eq("sku", filter.getSku())); }
	 * 
	 * // ilike %nome% if (StringUtils.isNotBlank(filter.getNome())) {
	 * criteria.add(Restrictions.ilike("nome",
	 * filter.getNome(),MatchMode.ANYWHERE)); }
	 * 
	 * return criteria.addOrder(Order.asc("nome")).list();
	 * 
	 * }
	 */

	public Produto porSKU(String sku) {
		// TODO Auto-generated method stub
		try {
			return manager.createQuery("from Produto where upper(sku) = :sku", Produto.class)
					.setParameter("sku", sku.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}

	}

	public Produto porId(Long id) {
		return manager.find(Produto.class, id);
	}

	public List<Produto> porNome(String nome) {
		// TODO Auto-generated method stub
		return this.manager.createQuery("from Produto where upper(nome) like :nome", Produto.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

}
