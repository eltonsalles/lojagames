package br.senac.tads4.piiv.repository.helper.usuario;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.senac.tads4.piiv.model.Usuario;

public class UsuarioRepositoryImpl implements UsuariosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Optional<Usuario> emailEStatus(String email) {
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and status = true", Usuario.class)
				.setParameter("email", email)
				.getResultList()
				.stream()
				.findFirst();
	}
}
