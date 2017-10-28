package br.senac.tads4.piiv.repository.helper.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.dto.ItemProdutoDto;
import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.filter.ProdutoFilter;

public class ProdutoRepositoryImpl implements ProdutosQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Produto> filtrar(ProdutoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getId())) {
				criteria.add(Restrictions.eq("id", filtro.getId()));
			}

			if (filtro.getTipoProduto() != null) {
				criteria.add(Restrictions.eq("tipoProduto", filtro.getTipoProduto()));
			}

			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}

			if (filtro.getPrecoCompraDe() != null) {
				criteria.add(Restrictions.ge("precoCompra", filtro.getPrecoCompraDe()));
			}

			if (filtro.getPrecoCompraAte() != null) {
				criteria.add(Restrictions.le("precoCompra", filtro.getPrecoCompraAte()));
			}

			if (filtro.getPrecoVendaDe() != null) {
				criteria.add(Restrictions.ge("precoVenda", filtro.getPrecoVendaDe()));
			}

			if (filtro.getPrecoVendaAte() != null) {
				criteria.add(Restrictions.le("precoVenda", filtro.getPrecoVendaAte()));
			}

			if (filtro.getEstoque() != null && filtro.getEstoque() == true) {
				criteria.add(Restrictions.eq("estoque", 0));
			}
		}

		return criteria.list();
	}

	@Override
	@Transactional(readOnly = true)
	public ItemProdutoDto itemProduto(Long id) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);
		if (id != null) {
			criteria.add(Restrictions.eq("id", id));
			Produto produto = (Produto) criteria.uniqueResult();
			ItemProdutoDto itemProduto = new ItemProdutoDto(produto.getImagens().get(0).getNome(), produto.getNome(),
					produto.getPrecoVenda(), produto.getEstoque(), produto.getPrecoVenda());

			return itemProduto;
		}
		
		return null;
	}
}
