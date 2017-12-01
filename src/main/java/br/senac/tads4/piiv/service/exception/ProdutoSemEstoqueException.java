package br.senac.tads4.piiv.service.exception;

public class ProdutoSemEstoqueException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProdutoSemEstoqueException(String message) {
		super(message);
	}
}
