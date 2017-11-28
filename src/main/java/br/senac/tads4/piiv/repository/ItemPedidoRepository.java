package br.senac.tads4.piiv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.ItemPedido;
import br.senac.tads4.piiv.model.Produto;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

	Optional<ItemPedido> findByProduto(Produto produto);
}
