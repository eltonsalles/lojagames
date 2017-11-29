package br.senac.tads4.piiv.service.event.historico;

import java.util.Set;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;

public class GerarHistoricoEvent {

	private Produto produto;

	private Set<ItemPedido> itensPedido;

	private TipoMovimentacao tipoMovimentacao;

	public GerarHistoricoEvent(Produto produto, TipoMovimentacao tipoMovimentacao) {
		this.produto = produto;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public GerarHistoricoEvent(Set<ItemPedido> itensPedido, TipoMovimentacao tipoMovimentacao) {
		this.itensPedido = itensPedido;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public Set<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public Boolean getEntrada() {
		return this.tipoMovimentacao.equals(TipoMovimentacao.ENTRADA);
	}

	public Boolean getVenda() {
		return this.tipoMovimentacao.equals(TipoMovimentacao.VENDA);
	}
}
