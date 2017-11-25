package br.senac.tads4.piiv.repository.helper.contato;

import java.util.List;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.repository.filter.ContatoFilter;

public interface ContatosQueries {

	public List<Contato> filtrar(ContatoFilter filtro);
}
