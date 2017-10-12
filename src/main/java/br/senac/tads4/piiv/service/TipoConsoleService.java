package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.TipoConsole;
import br.senac.tads4.piiv.repository.TipoConsoleRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela tipo_console
 * 
 * @author Elton
 *
 */
@Service
public class TipoConsoleService {

	@Autowired
	private TipoConsoleRepository tiposConsoles;
	
	public void salvar(TipoConsole tipoConsole) {
		tiposConsoles.save(tipoConsole);
	}
}
