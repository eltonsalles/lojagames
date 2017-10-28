package br.senac.tads4.piiv.repository.helper.produto;

import java.util.List;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.filter.ProdutoFilter;

public interface ProdutosQueries {

	public List<Produto> filtrar(ProdutoFilter filtro);
	
	public ItemProdutoDto itemProduto(Long id);
}
