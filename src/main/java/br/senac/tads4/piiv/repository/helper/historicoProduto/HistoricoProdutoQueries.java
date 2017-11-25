package br.senac.tads4.piiv.repository.helper.historicoProduto;

import java.util.List;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.repository.filter.HistoricoProdutoFilter;

public interface HistoricoProdutoQueries {

	public List<HistoricoProduto> filtrar(HistoricoProdutoFilter filtro);
	
}
