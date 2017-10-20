package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Jogo;
import br.senac.tads4.piiv.repository.JogoRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela jogo
 * 
 * @author Elton
 *
 */
@Service
public class JogoService {

	@Autowired
	private JogoRepository jogos;
	
	public void salvar(Jogo jogo) {
		jogo.setTipoProduto("JOGO");
		jogos.save(jogo);
	}
}
