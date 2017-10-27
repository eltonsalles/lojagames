package br.senac.tads4.piiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.Produto;
import br.senac.tads4.piiv.repository.helper.produto.ProdutosQueries;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutosQueries {

}
