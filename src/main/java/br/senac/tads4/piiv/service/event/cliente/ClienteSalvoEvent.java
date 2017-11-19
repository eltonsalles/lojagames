package br.senac.tads4.piiv.service.event.cliente;

import br.senac.tads4.piiv.model.Cliente;

public class ClienteSalvoEvent {

	private Cliente cliente;

	public ClienteSalvoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
