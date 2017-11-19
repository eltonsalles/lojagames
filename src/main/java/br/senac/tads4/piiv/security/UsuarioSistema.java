package br.senac.tads4.piiv.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Usuario;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	private Cliente cliente;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities, Cliente cliente) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
