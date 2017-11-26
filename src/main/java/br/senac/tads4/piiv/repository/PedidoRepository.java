package br.senac.tads4.piiv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.senac.tads4.piiv.model.Cliente;
import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.helper.pedido.PedidosQueries;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, PedidosQueries {

	 List<Pedido> findByCliente(Cliente cliente);
}
