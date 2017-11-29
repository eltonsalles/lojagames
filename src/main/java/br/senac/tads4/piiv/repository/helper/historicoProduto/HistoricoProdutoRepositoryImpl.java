package br.senac.tads4.piiv.repository.helper.historicoProduto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.repository.filter.HistoricoProdutoFilter;

public class HistoricoProdutoRepositoryImpl implements HistoricoProdutoQueries {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Retorna uma lista do histório do produto conforme o filtro
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<HistoricoProduto> filtrar(HistoricoProdutoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(HistoricoProduto.class);

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getIdProduto())) {
				criteria.add(Restrictions.eq("produto.id", filtro.getIdProduto()));
			}

			if (filtro.getTipoMovimentacao() != null) {
				criteria.add(Restrictions.eq("tipoMovimentacao", filtro.getTipoMovimentacao()));
			}
		}

		return criteria.list();
	}
}
