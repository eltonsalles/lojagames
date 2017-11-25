package br.senac.tads4.piiv.repository.filter;

import br.senac.tads4.piiv.model.enumerated.TipoMovimentacao;

public class HistoricoProdutoFilter {

	private TipoMovimentacao tipoMovimentacao;
	private Long idProduto;
		
	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	
}
