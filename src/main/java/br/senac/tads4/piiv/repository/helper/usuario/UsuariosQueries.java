package br.senac.tads4.piiv.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.senac.tads4.piiv.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> emailEStatus(String email);
	
	public List<String> permissoes(Usuario usuario);
}
