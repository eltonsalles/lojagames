package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.repository.ContatoRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela cliente
 * 
 * @author Elton
 *
 */
@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatos;
	
	public void salvar(Contato contato) {
		Contato c = contatos.findOne(contato.getId());
		c.setStatus("Respondida");
		c.setResposta(contato.getResposta());
		contatos.save(c);
	}
}
