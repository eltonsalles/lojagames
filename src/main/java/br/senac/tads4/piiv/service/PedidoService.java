package br.senac.tads4.piiv.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.model.EnderecoEntrega;
import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.FormaPagamento;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.service.event.pedido.PedidoSalvoEvent;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	public void salvar(Pedido pedido, List<ItemProdutoDto> carrinho) {
		// Data do pedido
		pedido.setDataPedido(LocalDate.now());

		// Carrinho de compra
		List<ItemPedido> itensPedido = new ArrayList<>();
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

		// Endereço de entrega
		EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
		enderecoEntrega.setCep(pedido.getCliente().getEnderecos().get(0).getCep());
		enderecoEntrega.setLogradouro(pedido.getCliente().getEnderecos().get(0).getLogradouro());
		enderecoEntrega.setNumero(pedido.getCliente().getEnderecos().get(0).getNumero());
		enderecoEntrega.setBairro(pedido.getCliente().getEnderecos().get(0).getBairro());
		enderecoEntrega.setCidade(pedido.getCliente().getEnderecos().get(0).getCidade());
		enderecoEntrega.setUf(pedido.getCliente().getEnderecos().get(0).getUf());
		if (!StringUtils.isEmpty(pedido.getCliente().getEnderecos().get(0).getComplemento())) {
			enderecoEntrega.setComplemento(pedido.getCliente().getEnderecos().get(0).getComplemento());
		}
		if (!StringUtils.isEmpty(pedido.getCliente().getEnderecos().get(0).getReferencia())) {
			enderecoEntrega.setReferencia(pedido.getCliente().getEnderecos().get(0).getReferencia());
		}
		pedido.setEnderecoEntrega(enderecoEntrega);

		// Status do pedido
		pedido.setStatus(StatusPedido.PENDENTE_PAGTO);

		pedidoRepository.save(pedido);
		
		publisher.publishEvent(new PedidoSalvoEvent(pedido));
	}
}
