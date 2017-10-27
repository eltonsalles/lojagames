package br.senac.tads4.piiv.repository.filter;

import java.math.BigDecimal;

import br.senac.tads4.piiv.model.enumerated.TipoProduto;

public class ProdutoFilter {

	private Long id;
	private TipoProduto tipoProduto;
	private String nome;
	private BigDecimal precoCompraDe;
	private BigDecimal precoCompraAte;
	private BigDecimal precoVendaDe;
	private BigDecimal precoVendaAte;
	private Boolean estoque;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoCompraDe() {
		return precoCompraDe;
	}

	public void setPrecoCompraDe(BigDecimal precoCompraDe) {
		this.precoCompraDe = precoCompraDe;
	}

	public BigDecimal getPrecoCompraAte() {
		return precoCompraAte;
	}

	public void setPrecoCompraAte(BigDecimal precoCompraAte) {
		this.precoCompraAte = precoCompraAte;
	}

	public BigDecimal getPrecoVendaDe() {
		return precoVendaDe;
	}

	public void setPrecoVendaDe(BigDecimal precoVendaDe) {
		this.precoVendaDe = precoVendaDe;
	}

	public BigDecimal getPrecoVendaAte() {
		return precoVendaAte;
	}

	public void setPrecoVendaAte(BigDecimal precoVendaAte) {
		this.precoVendaAte = precoVendaAte;
	}

	public Boolean getEstoque() {
		return estoque;
	}

	public void setEstoque(Boolean estoque) {
		this.estoque = estoque;
	}
}
