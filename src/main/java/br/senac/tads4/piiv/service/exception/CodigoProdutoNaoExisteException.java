package br.senac.tads4.piiv.service.exception;

public class CodigoProdutoNaoExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CodigoProdutoNaoExisteException(String message) {
		super(message);
	}
}
