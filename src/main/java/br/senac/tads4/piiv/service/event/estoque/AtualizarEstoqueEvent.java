package br.senac.tads4.piiv.service.event.estoque;

import java.util.Set;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.model.ItemPedido;

public class AtualizarEstoqueEvent {

	private HistoricoProduto historicoProduto;

	private Set<ItemPedido> itensPedido;

	private Long idPedido;

	private String acao;

	public AtualizarEstoqueEvent(HistoricoProduto historicoProduto, String acao) {
		this.historicoProduto = historicoProduto;
		this.acao = acao;
	}

	public AtualizarEstoqueEvent(Set<ItemPedido> itensPedido, String acao) {
		this.itensPedido = itensPedido;
		this.acao = acao;
	}

	public AtualizarEstoqueEvent(Long idPedido, String acao) {
		this.idPedido = idPedido;
		this.acao = acao;
	}

	public HistoricoProduto getHistoricoProduto() {
		return historicoProduto;
	}

	public Set<ItemPedido> getItensPedido() {
		return itensPedido;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public String getAcao() {
		return acao;
	}

	public Boolean getEntrada() {
		return this.acao.equalsIgnoreCase("entrar") && this.historicoProduto != null;
	}

	public Boolean getQuebra() {
		return this.acao.equalsIgnoreCase("baixar") && this.historicoProduto != null;
	}

	public Boolean getVenda() {
		return this.acao.equalsIgnoreCase("baixar") && this.itensPedido != null;
	}

	public Boolean getCancelarPedido() {
		return this.acao.equalsIgnoreCase("entrar") && this.idPedido != null;
	}
}
