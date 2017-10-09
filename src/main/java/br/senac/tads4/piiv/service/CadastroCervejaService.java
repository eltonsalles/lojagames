package br.senac.tads4.piiv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senac.tads4.piiv.model.Cerveja;
import br.senac.tads4.piiv.repository.Cervejas;

@Service
public class CadastroCervejaService {
	
	@Autowired
	private Cervejas cervejas;
	
	
	public void salvar(Cerveja cerveja) {
		cervejas.save(cerveja);
	}
}
