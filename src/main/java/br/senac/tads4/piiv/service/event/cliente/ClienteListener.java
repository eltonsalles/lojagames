package br.senac.tads4.piiv.service.event.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.Grupo;
import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.repository.GrupoRepository;
import br.senac.tads4.piiv.service.UsuarioService;

@Component
public class ClienteListener {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private GrupoRepository grupoRepository;

	/**
	 * Evento para criar um usuário para o cliente novo
	 * 
	 * @param evento
	 */
	@EventListener
	public void clienteSalvo(ClienteSalvoEvent evento) {
		List<Grupo> grupos = new ArrayList<>();
		Grupo grupo = grupoRepository.findOne(4L); // 4 = Cliente
		grupos.add(grupo);

		Usuario usuario = new Usuario();
		usuario.setNome(evento.getCliente().getNome());
		usuario.setEmail(evento.getCliente().getEmail());
		usuario.setSenha(evento.getCliente().getSenha());
		usuario.setStatus(true);
		usuario.setDataNascimento(evento.getCliente().getDataNascimento());
		usuario.setGrupos(grupos);

		usuarioService.salvar(usuario);
	}
}
