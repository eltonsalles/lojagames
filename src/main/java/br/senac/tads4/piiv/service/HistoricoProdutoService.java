package br.senac.tads4.piiv.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.HistoricoProdutoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.service.exception.CodigoProdutoNaoExiste;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela historico_produto
 * 
 * @author Fillipe
 *
 */
@Service
public class HistoricoProdutoService {

	@Autowired
	private HistoricoProdutoRepository historicoProdutoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	/**
	 * Salva uma nova movimentação de produto na base de dados
	 * 
	 * @param historicoProduto
	 */
	public void salvar(HistoricoProduto historicoProduto) {

		if (produtoRepository.findOne(historicoProduto.getProduto().getIdProduto()) == null) {
			throw new CodigoProdutoNaoExiste("O código informado não pertence a nenhum produto");
		}

		historicoProduto.setData(LocalDate.now());

		Produto produto = produtoRepository.findOne(historicoProduto.getProduto().getIdProduto());

		historicoProduto.setProduto(produto);

		historicoProdutoRepository.save(historicoProduto);
	}
}
