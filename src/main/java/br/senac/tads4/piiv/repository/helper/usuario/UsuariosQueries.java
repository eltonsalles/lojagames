package br.senac.tads4.piiv.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	Optional<Usuario> emailEStatus(String email);
	
	List<String> permissoes(Usuario usuario);
	
	List<Usuario> filtrar(UsuarioFilter filtro);
}
