package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Console;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;
import br.senac.tads4.piiv.repository.ConsoleRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela console
 * 
 * @author Elton
 *
 */
@Service
public class ConsoleService {

	@Autowired
	private ConsoleRepository consoles;
	
	public void salvar(Console console) {
		console.setTipoProduto(TipoProduto.CONSOLE);
		consoles.save(console);
	}
}
