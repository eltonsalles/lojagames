package br.senac.tads4.piiv.service.event.pedido;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.service.PedidoService;
import br.senac.tads4.piiv.service.exception.TransacaoCieloExcepetion;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.CieloEcommerce;
import cieloecommerce.sdk.ecommerce.Environment;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;

@Component
public class PedidoListener {

	private final String MERCHANT_ID = "fd18cb48-a7a5-41d6-915c-5b86fa3650b7";

	private final String MERCHANT_KEY = "AGTHAUCWKGWFBEZTMRDCHAZLXKEIEKBTETUSQFMD";
	
	@Autowired
	private PedidoService pedidoService;

	@EventListener(condition = "#evento.pagamentoCartaoCredito()")
	public void pedidoPagamentoCartaoCredito(PedidoSalvoEvent evento) {
		Merchant merchant = new Merchant(this.MERCHANT_ID, this.MERCHANT_KEY);

		Sale sale = new Sale(String.valueOf(evento.getPedido().getId()));

		sale.customer(evento.getPedido().getCliente().getNome());

		String valor = evento.getPedido().getValorTotal().toString();
		valor = valor.replaceAll("\\.", "");

		Payment payment = sale.payment(Integer.valueOf(valor), evento.getPedido().getParcelas());

		String vencimento = evento.getPedido().getDadosPagamento().getMesVencimento().substring(1) + "/" + evento.getPedido().getDadosPagamento().getAnoVencimento();
		
		payment
			.creditCard(evento.getPedido().getDadosPagamento().getCodigoSeguranca(), "Visa")
			.setExpirationDate(vencimento)
			.setCardNumber(evento.getPedido().getDadosPagamento().getCartao())
			.setHolder(evento.getPedido().getDadosPagamento().getNomeTitular());

		try {
			sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
			
			// Código 4 = Transação aprovada
			if (sale.getPayment().getReturnCode().equalsIgnoreCase("4")) {
				this.atualizarPagamentoPedido(evento.getPedido());
			} else {
				throw new TransacaoCieloExcepetion("Erro na transação de pagamento na cielo. Erro: " + sale.getPayment().getReturnCode());
			}

		} catch (CieloRequestException e) {
			throw new RuntimeException("Erro ao processar o pagamento com cartão de crédito", e);
		} catch (IOException e) {
			throw new RuntimeException("Erro na transação do pagamento", e);
		}
	}
	
	private void atualizarPagamentoPedido(Pedido pedido) {
		pedido.setDataPagamento(LocalDate.now());
		pedido.setStatus(StatusPedido.SEPARANDO);
		
		pedidoService.alterar(pedido);
	}
}
