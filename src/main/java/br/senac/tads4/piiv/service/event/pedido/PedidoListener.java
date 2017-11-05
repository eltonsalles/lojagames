package br.senac.tads4.piiv.service.event.pedido;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoListener {
	
	@EventListener
	public void pedidoSalvo(PedidoSalvoEvent evento) {
		System.out.println(">>>>>>>>> Chamou o evento!");
	}
}
