package br.senac.tads4.piiv.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
			double total=0;
			
			mv.addObject("total", total);
			mv.addObject("listaPedidos", pedidoRepository.filtrar(pedidoFilter));
			
			return mv;
		}
	
	
}
