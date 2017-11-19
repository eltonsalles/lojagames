package br.senac.tads4.piiv.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.repository.ClienteRepository;
import br.senac.tads4.piiv.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.emailEStatus(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));

		Cliente cliente = this.carregarCliente(email, this.getPermissoes(usuario));
		if (cliente != null) {
			return new UsuarioSistema(usuario, this.getPermissoes(usuario), cliente);
		}

		return new UsuarioSistema(usuario, this.getPermissoes(usuario));
	}

	/**
	 * Carrega as permissões
	 * 
	 * @param usuario
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		List<String> permissoes = usuarioRepository.permissoes(usuario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority("ROLE_" + p.toUpperCase())));

		return authorities;
	}

	/**
	 * Caso o usuário logado seja um cliente então o sistema carrega os seus dados
	 * 
	 * @param email
	 * @param authorities
	 * @return
	 */
	private Cliente carregarCliente(String email, Collection<? extends GrantedAuthority> authorities) {
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_CLIENTE")) {
				Optional<Cliente> clienteOptional = clienteRepository.findByEmailIgnoreCase(email);

				if (clienteOptional.isPresent()) {
					return clienteOptional.get();
				}
			}
		}

		return null;
	}
}
