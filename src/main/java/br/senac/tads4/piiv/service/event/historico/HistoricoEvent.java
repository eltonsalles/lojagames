package br.senac.tads4.piiv.service.event.historico;

import java.util.Set;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;

public class HistoricoEvent {

	private Produto produto;

	private Set<ItemPedido> itensPedido;

	private Long idPedido;

	private TipoMovimentacao tipoMovimentacao;

	public HistoricoEvent(Produto produto, TipoMovimentacao tipoMovimentacao) {
		this.produto = produto;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public HistoricoEvent(Set<ItemPedido> itensPedido, TipoMovimentacao tipoMovimentacao) {
		this.itensPedido = itensPedido;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public HistoricoEvent(Long id, TipoMovimentacao tipoMovimentacao) {
		this.idPedido = id;
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public Set<ItemPedido> getItensPedido() {
		return itensPedido;
	}
	
	public Long getIdPedido() {
		return idPedido;
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

	public Boolean getCancelamento() {
		return this.tipoMovimentacao.equals(TipoMovimentacao.CANCELAMENTO);
	}
}
