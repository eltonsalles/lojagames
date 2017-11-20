package br.senac.tads4.piiv.repository.helper.contato;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.repository.filter.ContatoFilter;

public class ContatoRepositoryImpl implements ContatosQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Contato> filtrar(ContatoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Contato.class);

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if (filtro.getStatus() != null && !filtro.getStatus().equalsIgnoreCase("todos")) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}

			if (filtro.getTipo() != null) {
				criteria.add(Restrictions.eq("tipo", filtro.getTipo()));
			}
		}

		return criteria.list();
	}
}
