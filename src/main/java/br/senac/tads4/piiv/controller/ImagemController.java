package br.senac.tads4.piiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.senac.tads4.piiv.dto.ImagemDto;
import br.senac.tads4.piiv.storage.ImagemStorage;
import br.senac.tads4.piiv.storage.ImagemStorageRunnable;

@RestController
@RequestMapping("/imagens")
public class ImagemController {
	
	@Autowired
	private ImagemStorage imagemStorage;

	@PostMapping
	public DeferredResult<ImagemDto> upload(@RequestParam("files[]") MultipartFile[] files) {
		DeferredResult<ImagemDto> resultado = new DeferredResult<>();
		
		Thread thread = new Thread(new ImagemStorageRunnable(files, resultado, imagemStorage));
		thread.start();

		return resultado;
	}

	@GetMapping("/temp/{nome:.*}")
	public byte[] recuperarImagemTemporaria(@PathVariable String nome) {
		return imagemStorage.recuperarImagemTemporaria(nome);
	}
	
	@GetMapping("/{nome:.*}")
	public byte[] recuperar(@PathVariable String nome) {
		return imagemStorage.recuperar(nome);
	}
}
