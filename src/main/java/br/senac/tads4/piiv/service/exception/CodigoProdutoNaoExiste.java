package br.senac.tads4.piiv.service.exception;

public class CodigoProdutoNaoExiste extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CodigoProdutoNaoExiste(String message) {
		super(message);
	}
}
