package br.senac.tads4.piiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.model.Pedido;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	
	@RequestMapping
	public ModelAndView pedido(Pedido pedido) {
		ModelAndView mv = new ModelAndView("site/pedido/VisualizarPedido");
		return mv;
	}	
}