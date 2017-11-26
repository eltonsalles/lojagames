package br.senac.tads4.piiv.service.exception;

public class PercentualDescontoMaiorQueCemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PercentualDescontoMaiorQueCemException(String message) {
		super(message);
	}
}
