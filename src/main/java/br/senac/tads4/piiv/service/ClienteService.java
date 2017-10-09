package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.repository.ClienteRepository;

/**
 * Classe responsável por persiste no banco de dados na tabela cliente
 * 
 * @author Elton
 *
 */
@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clientes;
	
	public void salvar(Cliente cliente) {
		clientes.save(cliente);
	}
}
