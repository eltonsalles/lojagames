package br.senac.tads4.piiv.model;

public enum Conexao {
	WIRELESS_BLUETOOTH("Wireless bluetooth");
	
	private String descricao;

	Conexao (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
