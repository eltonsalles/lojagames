package br.senac.tads4.piiv.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.senac.tads4.piiv.model.enumerated.StatusPedido;

public class PedidoFilter {

	private StatusPedido status;
	private LocalDate dataPedidoInicial;
	private LocalDate dataPedidoFinal;
	private BigDecimal lucro;

	public StatusPedido getStatus() {
		return status;
	}

	public BigDecimal getLucro() {
		return lucro;
	}

	public void setLucro(BigDecimal lucro) {
		this.lucro = lucro;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public LocalDate getDataPedidoInicial() {
		return dataPedidoInicial;
	}

	public void setDataPedidoInicial(LocalDate dataPedidoInicial) {
		this.dataPedidoInicial = dataPedidoInicial;
	}

	public LocalDate getDataPedidoFinal() {
		return dataPedidoFinal;
	}

	public void setDataPedidoFinal(LocalDate dataPedidoFinal) {
		this.dataPedidoFinal = dataPedidoFinal;
	}
}
