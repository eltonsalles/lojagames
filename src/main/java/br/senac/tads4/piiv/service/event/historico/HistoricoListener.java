package br.senac.tads4.piiv.service.event.historico;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.service.HistoricoProdutoService;

@Component
public class HistoricoListener {

	@Autowired
	private HistoricoProdutoService historicoProdutoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	/**
	 * Evento para gerar um histórico ao cadastrar um novo produto
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getEntrada()")
	public void gerarHistoricoProduto(HistoricoEvent evento) {
		this.salvarHistorico(evento.getProduto(), evento.getProduto().getEstoque(), evento.getTipoMovimentacao());
	}

	/**
	 * Evento para gerar um histórico ao realizar uma nova venda
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getVenda()")
	public void gerarHistoricoVenda(HistoricoEvent evento) {
		for (ItemPedido item : evento.getItensPedido()) {
			this.salvarHistorico(item.getProduto(), item.getQuantidade(), evento.getTipoMovimentacao());
		}
	}
	
	/**
	 * Evento para gerar um histórico ao cancelar uma nova venda
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.getCancelamento()")
	public void gerarHistoricoVendaCancelada(HistoricoEvent evento) {
		Pedido pedido = pedidoRepository.findOne(evento.getIdPedido());
		
		for (ItemPedido item : pedido.getItensPedido()) {
			this.salvarHistorico(item.getProduto(), item.getQuantidade(), evento.getTipoMovimentacao());
		}
	}

	/**
	 * Método para salvar um registro na tabela histórico de produto pelos eventos acima
	 * 
	 * @param produto
	 * @param quantidade
	 * @param tipoMovimentacao
	 */
	private void salvarHistorico(Produto produto, Integer quantidade, TipoMovimentacao tipoMovimentacao) {
		HistoricoProduto historicoProduto = new HistoricoProduto();
		historicoProduto.setData(LocalDate.now());
		historicoProduto.setProduto(produto);
		historicoProduto.setTipoMovimentacao(tipoMovimentacao);
		historicoProduto.setQuantidade(quantidade);

		historicoProdutoService.salvar(historicoProduto);
	}
}
