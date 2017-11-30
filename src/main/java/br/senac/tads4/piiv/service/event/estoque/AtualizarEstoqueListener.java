package br.senac.tads4.piiv.service.event.estoque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.repository.ProdutoRepository;

@Component
public class AtualizarEstoqueListener {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	/**
	 * Evento realizado quando acontece uma entrada de produto pelo formulário de movimentação
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getEntrada()")
	public void movimentacaoEntrada(AtualizarEstoqueEvent evento) {
		this.entrarEstoque(evento.getHistoricoProduto().getProduto(), evento.getHistoricoProduto().getQuantidade());
	}

	/**
	 * Evento realizado quando acontece uma saída (quebra) de produto pelo formulário de movimentação
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getQuebra()")
	public void movimentacaoQuebra(AtualizarEstoqueEvent evento) {
		this.baixarEstoque(evento.getHistoricoProduto().getProduto(), evento.getHistoricoProduto().getQuantidade());
	}

	/**
	 * Evento realizado quando acontece uma venda
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getVenda()")
	public void venda(AtualizarEstoqueEvent evento) {
		for (ItemPedido item : evento.getItensPedido()) {
			this.baixarEstoque(item.getProduto(), item.getQuantidade());
		}
	}
	
	/**
	 * Evento realizado ao cancelar um pedido
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getCancelarPedido()")
	public void cancelarPedido(AtualizarEstoqueEvent evento) {
		Pedido pedido = pedidoRepository.findOne(evento.getIdPedido());
		
		for (ItemPedido item : pedido.getItensPedido()) {
			this.entrarEstoque(item.getProduto(), item.getQuantidade());
		}
	}

	private void baixarEstoque(Produto produto, Integer quantidade) {
		Produto p = produtoRepository.findOne(produto.getIdProduto());
		p.setEstoque(p.getEstoque() - quantidade);

		produtoRepository.save(p);
	}

	private void entrarEstoque(Produto produto, Integer quantidade) {
		Produto p = produtoRepository.findOne(produto.getIdProduto());
		p.setEstoque(p.getEstoque() + quantidade);

		produtoRepository.save(p);
	}
}
