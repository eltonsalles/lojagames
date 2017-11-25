package br.senac.tads4.piiv.service;

import java.time.LocalDate;

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

	/**
	 * Cadastra um novo contato feito pelo formulário do site
	 * 
	 * @param contato
	 * @return
	 */
	public Long salvar(Contato contato) {
		contato.setStatus("Pendente");
		contato.setData(LocalDate.now());

		contatos.saveAndFlush(contato);

		return contato.getId();
	}

	/**
	 * Salva a resposta do contato
	 * 
	 * @param contato
	 */
	public void alterar(Contato contato) {
		Contato c = contatos.findOne(contato.getId());
		c.setStatus("Respondida");
		c.setResposta(contato.getResposta());

		contatos.save(c);
	}
}
