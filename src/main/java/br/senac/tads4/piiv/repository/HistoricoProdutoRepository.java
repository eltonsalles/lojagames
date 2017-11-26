package br.senac.tads4.piiv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.HistoricoProduto;
import br.senac.tads4.piiv.repository.helper.historicoProduto.HistoricoProdutoQueries;

@Repository
public interface HistoricoProdutoRepository extends JpaRepository<HistoricoProduto, Long>, HistoricoProdutoQueries {

}
