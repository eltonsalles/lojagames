package br.senac.tads4.piiv.repository.filter;

import java.time.LocalDate;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;

public class PedidoFilter {

	private StatusPedido status;
	private LocalDate dataPedidoInicial;
	private LocalDate dataPedidoFinal;
	private Double lucro;
	

	public StatusPedido getStatus() {
		return status;
	}

	public Double getLucro() {
		return lucro;
	}

	public void setLucro(Double lucro) {
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
