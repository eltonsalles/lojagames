package br.senac.tads4.piiv.model.enumerated;

public enum Meses {
	_1("Janeiro"),
	_2("Fevereiro"),
	_3("Março"),
	_4("Abril"),
	_5("Maio"),
	_6("Junho"),
	_7("Julho"),
	_8("Agosto"),
	_9("Setembro"),
	_10("Outubro"),
	_11("Novembro"),
	_12("Dezembro");
	
	private String descricao;
	
	Meses (String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
