package br.senac.tads4.piiv.service.event.produto;

import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Produto;

public class ProdutoSalvoEvent {

	private Produto produto;

	public ProdutoSalvoEvent(Produto produto) {
		super();
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public boolean temImagem() {
		return !StringUtils.isEmpty(produto.getImagens());
	}
}
