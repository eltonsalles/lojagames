package br.senac.tads4.piiv.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.senac.tads4.piiv.dto.ImagemDto;

public class ImagemStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<ImagemDto> resultado;
	private ImagemStorage imagemStorage;
	
	public ImagemStorageRunnable(MultipartFile[] files, DeferredResult<ImagemDto> resultado, ImagemStorage imagemStorage) {
		this.files = files;
		this.resultado = resultado;
		this.imagemStorage = imagemStorage;
	}

	@Override
	public void run() {
		String nome = this.imagemStorage.salvarTemporariamente(files);
		String contentType = files[0].getContentType();
		this.resultado.setResult(new ImagemDto(nome, contentType));
	}
}
