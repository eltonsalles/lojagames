package br.senac.tads4.piiv.service.event.pedido;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.service.PedidoService;
import br.senac.tads4.piiv.service.exception.PagamentoCartaoCreditoException;
import br.senac.tads4.piiv.service.exception.PagamentoException;
import br.senac.tads4.piiv.service.exception.TransacaoCieloExcepetion;
import cieloecommerce.sdk.Merchant;
import cieloecommerce.sdk.ecommerce.CieloEcommerce;
import cieloecommerce.sdk.ecommerce.Environment;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.request.CieloRequestException;

@Component
public class PedidoListener {

	private static final String MERCHANT_ID = "fd18cb48-a7a5-41d6-915c-5b86fa3650b7";

	private static final String MERCHANT_KEY = "AGTHAUCWKGWFBEZTMRDCHAZLXKEIEKBTETUSQFMD";
	
	@Autowired
	private PedidoService pedidoService;

	/**
	 * Método disparado quando um pedido pago com cartão de crédito é salvo no sistema.
	 * Aqui é feito a tentativa de pagamento com a API da Cielo
	 * 
	 * @param evento
	 */
	@EventListener(condition = "#evento.pagamentoCartaoCredito()")
	public void pedidoPagamentoCartaoCredito(PedidoSalvoEvent evento) {
		Merchant merchant = new Merchant(MERCHANT_ID, MERCHANT_KEY);

		// Criado uma venda com o código do pedido
		Sale sale = new Sale(String.valueOf(evento.getPedido().getId()));

		sale.customer(evento.getPedido().getCliente().getNome());

		// Colocado o valor no padrão necessário para a API da Cielo
		String valor = evento.getPedido().getValorTotal().toString();
		valor = valor.replaceAll("\\.", "");

		// Valor e número de parcelas (1 = à vista)
		Payment payment = sale.payment(Integer.valueOf(valor), evento.getPedido().getParcelas());

		String vencimento = evento.getPedido().getDadosPagamento().getMesVencimento().substring(1) + "/" + evento.getPedido().getDadosPagamento().getAnoVencimento();
		
		// Dados de pagamento
		payment
			.creditCard(evento.getPedido().getDadosPagamento().getCodigoSeguranca(), this.bandeira(evento.getPedido().getDadosPagamento().getCartao()))
			.setExpirationDate(vencimento)
			.setCardNumber(evento.getPedido().getDadosPagamento().getCartao())
			.setHolder(evento.getPedido().getDadosPagamento().getNomeTitular());

		try {
			// Concretização do pagamento
			sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);
			
			// Código 4 = Transação aprovada
			if (sale.getPayment().getReturnCode().equalsIgnoreCase("4")) {
				this.atualizarPagamentoPedido(evento.getPedido());
			} else {
				throw new TransacaoCieloExcepetion("Erro na transação de pagamento na cielo. Erro: " + sale.getPayment().getReturnCode());
			}

		} catch (CieloRequestException e) {
			throw new PagamentoCartaoCreditoException("Erro ao processar o pagamento com cartão de crédito");
		} catch (IOException e) {
			throw new PagamentoException("Erro na transação do pagamento");
		}
	}
	
	/**
	 * Método simples para retornar a bandeira do cartão
	 * 
	 * @param cartao
	 * @return
	 */
	private String bandeira(String cartao) {
		String primeiroNumero = cartao.substring(0, 1);
		
		if (primeiroNumero.equalsIgnoreCase("5")) {
			return "Mastercard";
		}
		
		return "Visa";
	}
	
	/**
	 * Método para atualizar os dados do pedido quando o pagamento com cartão de crédito for bem sucedido
	 * 
	 * @param pedido
	 */
	private void atualizarPagamentoPedido(Pedido pedido) {
		pedido.setDataPagamento(LocalDate.now());
		pedido.setStatus(StatusPedido.SEPARANDO);
		
		pedidoService.alterar(pedido);
	}
}
