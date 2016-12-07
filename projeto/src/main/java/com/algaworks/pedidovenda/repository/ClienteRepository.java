package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class ClienteRepository implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cliente porId(Long id) {
		return this.manager.find(Cliente.class, id);
	}
	
	public List<Cliente> porNome(String nome) {
		return this.manager.createQuery("from Cliente " +
				"where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {

		Session session = manager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Cliente.class);
		if(StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.like("nome", filtro.getNome(),MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(filtro.getEmail())) {
			criteria.add(Restrictions.like("email", filtro.getEmail(),MatchMode.ANYWHERE));
		}
		
		if (filtro.getTipo() != null && filtro.getTipo().length > 0) {
			// adicionamos uma restrição "in", passando um array de constantes da enum Tipo
			criteria.add(Restrictions.in("tipo", filtro.getTipo()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();

	}

	public Cliente salvar(Cliente cliente) {
		return manager.merge(cliente);
	}

	@Transactional
	public void remover(Cliente cliente)
	{
		try {
			cliente = porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			// TODO: handle exception
			throw new NegocioException("Cliente não pode ser excluido");
		}
		
	}
	
	
}