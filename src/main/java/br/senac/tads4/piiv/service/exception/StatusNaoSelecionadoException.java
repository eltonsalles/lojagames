package br.senac.tads4.piiv.service.exception;

public class StatusNaoSelecionadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public StatusNaoSelecionadoException(String message) {
		super(message);
	}
}
