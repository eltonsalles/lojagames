package br.senac.tads4.piiv.service.event.cliente;

import br.senac.tads4.piiv.model.Cliente;

public class ClienteSalvoEvent {

	private Cliente cliente;

	private String emailAntigo;

	public ClienteSalvoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public ClienteSalvoEvent(Cliente cliente, String emailAntigo) {
		this.cliente = cliente;
		this.emailAntigo = emailAntigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getEmailAntigo() {
		return emailAntigo;
	}

	public Boolean getClienteNovo() {
		return this.emailAntigo == null;
	}

	public Boolean getAlteracaoEmail() {
		return this.emailAntigo != null;
	}
}
