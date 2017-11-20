package br.senac.tads4.piiv.model.enumerated;

public enum TipoContato {
	DUVIDAS("Dúvidas"),
	RECLAMACOES("Reclamações"),
	SUGESTOES("Sugestões");
	
	private String descricao;
	
	TipoContato(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
