package br.senac.tads4.piiv.storage;

import org.springframework.web.multipart.MultipartFile;

public interface ImagemStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarImagemTemporaria(String nome);
	
	public byte[] recuperar(String imagem);
	
	public void salvar(String imagem);

	public byte[] recuperarThumbnail(String imagemProduto);
}
