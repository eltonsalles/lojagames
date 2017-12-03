package br.senac.tads4.piiv.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.storage.ImagemStorage;

@Component
public class Mailer {

	private static Logger LOGGER = LoggerFactory.getLogger(Mailer.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine thymeleaf;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ImagemStorage imagemStorage;

	@Async
	public void enviarPedido(Pedido pedido) {
		this.enviarEmail(pedido, "Pedido Realizado com Sucesso!", "ResumoVenda");
	}

	@Async
	public void enviarAtualizaoStatus(Pedido pedido) {
		this.enviarEmail(pedido, "Status do Pedido", "ResumoPedido");
	}

	private void enviarEmail(Pedido pedido, String titulo, String template) {
		Context context = new Context();
		
		Pedido p = pedidoRepository.findOne(pedido.getId());
		context.setVariable("pedido", p);
		context.setVariable("statusPedido", StatusPedido.values());

		Map<String, String> imagens = new HashMap<>();
		for (ItemPedido item : p.getItensPedido()) {
			Produto produto = item.getProduto();
			String cid = "imagem-" + produto.getIdProduto();
			context.setVariable(cid, cid);

			imagens.put(cid, produto.getImagens().get(0).getNome() + "|" + produto.getImagens().get(0).getContentType());
		}

		try {
			String email = this.thymeleaf.process("site/mail/" + template, context);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom("eltonrms.leite@hotmail.com");
			helper.setTo(p.getCliente().getEmail());
			helper.setSubject("The Code - " + titulo + "!");
			helper.setText(email, true);

			for (String cid : imagens.keySet()) {
				String nomeImagem = imagens.get(cid).split("\\|")[0];
				String contentType = imagens.get(cid).split("\\|")[1];

				byte[] imagem = imagemStorage.recuperarThumbnail(nomeImagem);
				helper.addInline(cid, new ByteArrayResource(imagem), contentType);
			}

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			LOGGER.error("Erro enviando e-mail", e);
		}

	}
}
