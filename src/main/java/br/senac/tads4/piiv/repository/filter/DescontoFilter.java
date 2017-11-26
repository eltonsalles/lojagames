package br.senac.tads4.piiv.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.senac.tads4.piiv.model.TipoConsole;
import br.senac.tads4.piiv.model.enumerated.TipoProduto;

public class DescontoFilter {

	private Long idProduto;
	private String nomeProduto;
	private TipoConsole tipoConsole;
	private TipoProduto tipoProduto;
	private BigDecimal precoVendaDe;
	private BigDecimal precoVendaAte;
	private BigDecimal percentualDesconto;
	private LocalDate descontoAte;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public TipoConsole getTipoConsole() {
		return tipoConsole;
	}

	public void setTipoConsole(TipoConsole tipoConsole) {
		this.tipoConsole = tipoConsole;
	}

	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
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

	public BigDecimal getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(BigDecimal percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public LocalDate getDescontoAte() {
		return descontoAte;
	}

	public void setDescontoAte(LocalDate descontoAte) {
		this.descontoAte = descontoAte;
	}
}
