package br.senac.tads4.piiv.repository.helper.pedido;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.filter.PedidoFilter;

public class PedidoRepositoryImpl implements PedidosQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> filtrar(PedidoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Pedido.class);

		if (filtro != null) {

			if (filtro.getDataPedidoInicial() != null && filtro.getDataPedidoFinal() != null) {
				criteria.add(Restrictions.between("dataPedido", filtro.getDataPedidoInicial(), filtro.getDataPedidoFinal()));
			}
			
			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
		}
		// usando Distinct no sql.
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
		
	}
}