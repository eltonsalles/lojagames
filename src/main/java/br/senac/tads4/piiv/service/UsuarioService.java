package br.senac.tads4.piiv.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.repository.UsuarioRepository;
import br.senac.tads4.piiv.service.exception.EmailUsuarioJaCadastradoException;
import br.senac.tads4.piiv.service.exception.SenhaUsuarioObrigatoriaException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Método para salvar um novo usuário
	 * 
	 * @param usuario
	 */
	public void salvar(Usuario usuario) {
		Optional<Usuario> emailOptional = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());

		if (emailOptional.isPresent()) {
			throw new EmailUsuarioJaCadastradoException("O e-mail informado já está cadastrado");
		}

		if (usuario.getNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaUsuarioObrigatoriaException("O campo senha é obrigatório");
		}

		if (usuario.getNovo()) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
			usuario.setConfirmacaoSenha(usuario.getSenha());
		}

		usuarioRepository.save(usuario);
	}

	/**
	 * Método para alterar os dados do usuário
	 * 
	 * @param usuario
	 */
	public void alterar(Usuario usuario) {
		Usuario u = usuarioRepository.findOne(usuario.getId());

		if (!StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else {
			usuario.setSenha(u.getSenha());
		}
		usuario.setConfirmacaoSenha(usuario.getSenha());

		usuarioRepository.save(usuario);
	}

	/**
	 * Exclui um usuário pelo id
	 * 
	 * @param id
	 */
	public void excluir(Long id) {
		usuarioRepository.delete(id);
	}
}
