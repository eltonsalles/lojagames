package br.senac.tads4.piiv.controller;
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
		 * @param id
		 * @return
		 */
		@RequestMapping(value = "/vendas")
		public ModelAndView pedido(PedidoFilter pedidoFilter) {
			ModelAndView mv = new ModelAndView("backoffice/relatorio/RelatorioVendas");
			double total = 0;
			
			List<Pedido> pedidos =   this.pedidoRepository.filtrar(pedidoFilter);
			
			for(int i=0; i<pedidos.size(); i++)
				total = total + pedidos.get(i).getValorSubTotal().doubleValue();
			
			mv.addObject("statusAll", StatusPedido.values());
			mv.addObject("total", total);
			mv.addObject("listaPedidos",pedidos);
			
			return mv;
		}
	
	
}
