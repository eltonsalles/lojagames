package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.ProdutoRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela produto
 * 
 * @author Elton
 *
 */
@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtos;
	
	public void salvar(Produto produto) {
		produtos.save(produto);
	}
}
