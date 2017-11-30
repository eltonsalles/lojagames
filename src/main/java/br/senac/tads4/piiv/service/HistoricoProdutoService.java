package br.senac.tads4.piiv.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.repository.HistoricoProdutoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;
import br.senac.tads4.piiv.service.event.estoque.AtualizarEstoqueEvent;
import br.senac.tads4.piiv.service.exception.CodigoProdutoNaoExisteException;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela
 * historico_produto
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
	
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * Salva uma nova movimentação de produto na base de dados
	 * 
	 * @param historicoProduto
	 */
	public void salvar(HistoricoProduto historicoProduto) {
		if (produtoRepository.findOne(historicoProduto.getProduto().getIdProduto()) == null) {
			throw new CodigoProdutoNaoExisteException("O código informado não pertence a nenhum produto");
		}
		
		Produto produto = produtoRepository.findOne(historicoProduto.getProduto().getIdProduto());
		historicoProduto.setProduto(produto);
		historicoProduto.setData(LocalDate.now());

		historicoProdutoRepository.save(historicoProduto);
		
		if (historicoProduto.getTipoMovimentacao().equals(TipoMovimentacao.QUEBRA)) {
			publisher.publishEvent(new AtualizarEstoqueEvent(historicoProduto, "baixar"));
		} else if (historicoProduto.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)) {
			publisher.publishEvent(new AtualizarEstoqueEvent(historicoProduto, "entrar"));
		}
	}
}
