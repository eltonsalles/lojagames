package br.senac.tads4.piiv.service;

import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

	// 10% de desconto
	private Double percentualDesconto = 0.9;
	
	// Número máximo de parcelas
	private Integer maximoParcelas = 10;

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public Integer getMaximoParcelas() {
		return maximoParcelas;
	}

	public void setMaximoParcelas(Integer maximoParcelas) {
		this.maximoParcelas = maximoParcelas;
	}
}
