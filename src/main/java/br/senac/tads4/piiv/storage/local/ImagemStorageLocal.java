package br.senac.tads4.piiv.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import br.senac.tads4.piiv.storage.ImagemStorage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class ImagemStorageLocal implements ImagemStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImagemStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public ImagemStorageLocal() {
		this(getDefault().getPath(System.getenv("HOME"), ".thecodeimagens"));
	}
	
	public ImagemStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		String novoNome = null;
		
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a imagem na pasta temporária", e);
			}
		}
		
		return novoNome;
	}
	
	@Override
	public byte[] recuperarImagemTemporaria(String nome) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a imagem temporária", e);
		}
	}
	
	@Override
	public byte[] recuperar(String imagem) {
		try {
			return Files.readAllBytes(this.local.resolve(imagem));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a imagem final", e);
		}
	}
	
	@Override
	public void salvar(String imagem) {
		try {
			Files.move(this.localTemporario.resolve(imagem), this.local.resolve(imagem));
		} catch (IOException e) {
			throw new RuntimeException("Erro movendo a foto para o destino final", e);
		}
		
		try {
			Thumbnails.of(this.local.resolve(imagem).toString()).size(174,  174).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro gerando thumbnail", e);
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String imagemProduto) {
		return this.recuperar("thumbnail." + imagemProduto);
	}
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.localTemporario = getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pasta para salvar imagens");
				LOGGER.debug("Pasta default: " + this.local.toAbsolutePath());
				LOGGER.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar imagem", e);
		}
		
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
		}
		
		return novoNome;
	}
}
