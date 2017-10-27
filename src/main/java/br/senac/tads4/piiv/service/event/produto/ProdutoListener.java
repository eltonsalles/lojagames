package br.senac.tads4.piiv.service.event.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.senac.tads4.piiv.storage.ImagemStorage;

@Component
public class ProdutoListener {

	@Autowired
	private ImagemStorage imagemStorage;
	
	@EventListener(condition = "#evento.temImagem()")
	public void produtoSalvo(ProdutoSalvoEvent evento) {
		imagemStorage.salvar(evento.getProduto().getImagens().get(0).getNome());
	}
}
