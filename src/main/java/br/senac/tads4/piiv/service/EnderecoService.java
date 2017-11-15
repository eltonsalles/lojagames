package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Endereco;
import br.senac.tads4.piiv.repository.EnderecoRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela cliente
 * 
 * @author Douglas Oliveira
 *
 */
@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecos;

	public void salvar(Endereco endereco) {
		enderecos.save(endereco);
	}
}
