package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Genero;
import br.senac.tads4.piiv.repository.GeneroRepository;

/**
 * Classe responsável por persistir os dados no banco de dados na tabela genero
 * 
 * @author Elton
 *
 */
@Service
public class GeneroService {

	@Autowired
	private GeneroRepository generos;
	
	public void salvar(Genero genero) {
		generos.save(genero);
	}
}
