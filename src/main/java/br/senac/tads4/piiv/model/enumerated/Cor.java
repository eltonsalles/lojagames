package br.senac.tads4.piiv.model.enumerated;

public enum Cor {
	PRETO("Preto"),
	BRANCO("Branco"),
	PERSONALIZADO("Personalizado");
	
	private String descricao;
	
	Cor(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
