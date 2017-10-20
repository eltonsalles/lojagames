package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Controle;
import br.senac.tads4.piiv.repository.ControleRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela controle
 * 
 * @author Elton
 *
 */
@Service
public class ControleService {

	@Autowired
	private ControleRepository controles;
	
	public void salvar(Controle controle) {
		controle.setTipoProduto("CONTROLE");
		controles.save(controle);
	}
}
