package br.senac.tads4.piiv.storage;

import org.springframework.web.multipart.MultipartFile;

public interface ImagemStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarImagemTemporaria(String nome);
}
