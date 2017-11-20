package br.senac.tads4.piiv.repository.filter;

import br.senac.tads4.piiv.model.enumerated.TipoContato;

public class ContatoFilter {

	private Long id;
	private String status;
	private TipoContato tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TipoContato getTipo() {
		return tipo;
	}

	public void setTipo(TipoContato tipo) {
		this.tipo = tipo;
	}
}
