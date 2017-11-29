package br.senac.tads4.piiv.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.Pedido;
import br.senac.tads4.piiv.model.enumerated.StatusPedido;
import br.senac.tads4.piiv.repository.PedidoRepository;
import br.senac.tads4.piiv.repository.filter.PedidoFilter;

@Controller
@RequestMapping("/admin/relatorio")
public class RelatorioVendaController {

	@Autowired
	private PedidoRepository pedidoRepository;

	/**
	 * Permite visualizar o relatorio de vendas
	 * 
	 * @param pedidoFilter
	 * @return
	 */
	@RequestMapping(value = "/vendas")
	public ModelAndView pedido(PedidoFilter pedidoFilter) {
		ModelAndView mv = new ModelAndView("backoffice/relatorio/RelatorioVendas");
		BigDecimal total = BigDecimal.ZERO;

		List<Pedido> pedidos = this.pedidoRepository.filtrar(pedidoFilter);
		total = this.CalcularTotal(pedidos);

		mv.addObject("statusAll", StatusPedido.values());
		mv.addObject("total", total);
		mv.addObject("listaPedidos", pedidos);

		return mv;
	}

	/**
	 * Calcula valor total dos pedidos
	 * 
	 * @param pedidos
	 * @return
	 */
	private BigDecimal CalcularTotal(List<Pedido> pedidos) {
		BigDecimal total = BigDecimal.ZERO;

		for (Pedido p : pedidos)
			total = total.add(p.getValorSubTotal());

		return total;
	}
}
