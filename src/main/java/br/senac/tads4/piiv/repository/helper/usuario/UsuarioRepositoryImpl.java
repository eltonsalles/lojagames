package br.senac.tads4.piiv.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Grupo;
import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.model.UsuarioGrupo;
import br.senac.tads4.piiv.repository.filter.UsuarioFilter;

public class UsuarioRepositoryImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * Método para verificar a existência de um usuário pelo email e status ativo
	 */
	@Override
	public Optional<Usuario> emailEStatus(String email) {
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and status = true", Usuario.class)
				.setParameter("email", email)
				.getResultList()
				.stream()
				.findFirst();
	}

	/**
	 * Método para recuperar as permissões do usuário 
	 */
	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	/**
	 * Método para consultar os usuários conforme o filtro
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> filtrar(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
			}

			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for (Long idGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.id", idGrupo));
					dc.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("id", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
}
