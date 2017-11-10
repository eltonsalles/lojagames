package br.senac.tads4.piiv.service.event.pedido;

import br.senac.tads4.piiv.model.Pedido;

public class PedidoSalvoEvent {

	private Pedido pedido;

	public PedidoSalvoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}
	
	public boolean pagamentoCartaoCredito() {
		return this.pedido.getTipoPagamento().name().equalsIgnoreCase("CARTAO_CREDITO");
	}
}
