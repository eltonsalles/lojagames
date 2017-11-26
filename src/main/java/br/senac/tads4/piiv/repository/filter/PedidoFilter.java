package br.senac.tads4.piiv.repository.filter;

import java.time.LocalDate;

public class PedidoFilter {

	
	private LocalDate dataPedidoInicial;
	private LocalDate dataPedidoFinal;

	
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
