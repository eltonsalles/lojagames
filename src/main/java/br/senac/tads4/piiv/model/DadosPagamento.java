package br.senac.tads4.piiv.model;

import java.io.Serializable;

public class DadosPagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer parcelas;

	private String nomeTitular;

	private String cartao;

	private String mesVencimento;

	private String anoVencimento;

	private String codigoSeguranca;

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public String getNomeTitular() {
		return nomeTitular;
	}

	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}

	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}

	public String getMesVencimento() {
		return mesVencimento;
	}

	public void setMesVencimento(String mesVencimento) {
		this.mesVencimento = mesVencimento;
	}

	public String getAnoVencimento() {
		return anoVencimento;
	}

	public void setAnoVencimento(String anoVencimento) {
		this.anoVencimento = anoVencimento;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
}
