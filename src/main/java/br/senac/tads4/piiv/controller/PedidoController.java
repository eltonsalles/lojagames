package br.senac.tads4.piiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PedidoController {
	
	
	@RequestMapping("/historico")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("site/pedido/Historico");
		return mv;
	}
	
}