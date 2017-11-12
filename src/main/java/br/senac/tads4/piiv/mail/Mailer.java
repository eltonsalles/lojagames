package br.senac.tads4.piiv.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.model.Pedido;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void enviar(Pedido pedido) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("eltonrms.leite@hotmail.com");
		mensagem.setTo(pedido.getCliente().getEmail());
		mensagem.setSubject("Pedido Realizado com Sucesso");
		mensagem.setText("Obrigado, seu pedido foi realizado.");
		
		mailSender.send(mensagem);
	}
}
