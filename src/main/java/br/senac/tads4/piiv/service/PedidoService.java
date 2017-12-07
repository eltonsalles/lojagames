package br.senac.tads4.piiv.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.mail.Mailer;
import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.FormaPagamento;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.service.event.estoque.AtualizarEstoqueEvent;
import br.senac.tads4.piiv.service.event.historico.HistoricoEvent;
import br.senac.tads4.piiv.service.event.pedido.PedidoSalvoEvent;
import br.senac.tads4.piiv.service.exception.PagamentoCartaoCreditoException;
import br.senac.tads4.piiv.service.exception.PagamentoException;
import br.senac.tads4.piiv.service.exception.StatusNaoSelecionadoException;
import br.senac.tads4.piiv.service.exception.TransacaoCieloExcepetion;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private Mailer mailer;

	public Long salvar(Pedido pedido, List<ItemProdutoDto> carrinho) {
		// Data do pedido
		pedido.setDataPedido(LocalDate.now());

		// Carrinho de compra
		Set<ItemPedido> itensPedido = new HashSet<>();
		for (ItemProdutoDto itemProdutoDto : carrinho) {
			Produto produto = new Produto();
			produto.setIdProduto(itemProdutoDto.getId());

			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setProduto(produto);
			itemPedido.setPedido(pedido);
			itemPedido.setQuantidade(itemProdutoDto.getQtde());
			itemPedido.setValorUnitario(itemProdutoDto.getPreco());

			itensPedido.add(itemPedido);
		}
		pedido.setItensPedido(itensPedido);

		// Forma de pagamento
		if (pedido.getTipoPagamento().toString().equalsIgnoreCase("BOLETO")) {
			pedido.setFormaPagamento(FormaPagamento.A_VISTA);
		} else if (pedido.getTipoPagamento().toString().equalsIgnoreCase("CARTAO_CREDITO")
				&& pedido.getParcelas() == 1) {
			pedido.setFormaPagamento(FormaPagamento.A_VISTA);
		} else {
			pedido.setFormaPagamento(FormaPagamento.PARCELADO);
		}

		// Status do pedido
		pedido.setStatus(StatusPedido.PENDENTE_PAGTO);

		pedidoRepository.saveAndFlush(pedido);

		try {
			publisher.publishEvent(new PedidoSalvoEvent(pedido));
			publisher.publishEvent(new HistoricoEvent(itensPedido, TipoMovimentacao.VENDA));
			publisher.publishEvent(new AtualizarEstoqueEvent(itensPedido, "baixar"));
		} catch (TransacaoCieloExcepetion | PagamentoCartaoCreditoException | PagamentoException e) {
			// Erro com a transação na cielo
		}

		return pedido.getId();
	}

	public void alterar(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	/**
	 * Altera o status do pedido pesquisado 
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public void alterarStatus(Long id, String status) {
		Boolean enviarEmail = true;
		Pedido pedido = pedidoRepository.findOne(id);
		
		try {
			if (status.equals("CANCELADO")) {
				pedido.setStatus(StatusPedido.CANCELADO);
				
			} else if (status.equals("SEPARACAO")) {
				pedido.setStatus(StatusPedido.SEPARANDO);
				pedido.setDataPagamento(LocalDate.now());
				
			} else if (status.equals("TRANSPORTE")) {
				pedido.setStatus(StatusPedido.TRANSPORTE);
				pedido.setDataSeparacao(LocalDate.now());
				
			} else if (status.equals("ENTREGA")) {
				pedido.setStatus(StatusPedido.ENTREGUE);
				pedido.setDataTransporte(LocalDate.now());
				enviarEmail = false;
				
			} else {
				pedido.setDataEntrega(LocalDate.now());
			}
		} catch (Exception e) {
			throw new StatusNaoSelecionadoException("Status não selecionado");
		}
		 
		pedidoRepository.save(pedido);
		
		if (enviarEmail) {
			this.mailer.enviarAtualizaoStatus(pedido);
		}
		
		if (status.equals("CANCELADO")) {
			publisher.publishEvent(new HistoricoEvent(id, TipoMovimentacao.CANCELAMENTO));
			publisher.publishEvent(new AtualizarEstoqueEvent(id, "entrar"));
		}
	}
}
