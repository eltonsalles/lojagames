package br.senac.tads4.piiv.repository.helper.pedido;

import java.util.List;

import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.repository.filter.PedidoFilter;

public interface PedidosQueries {

	public List<Pedido> filtrar(PedidoFilter filtro);

}