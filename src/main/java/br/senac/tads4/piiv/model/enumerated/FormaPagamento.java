package br.senac.tads4.piiv.model.enumerated;

public enum FormaPagamento {
	A_VISTA("À vista"),
	PARCELADO("Parcelado");
	
	private String descricao;
	
	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
