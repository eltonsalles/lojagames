package br.senac.tads4.piiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/")
public class ContatoController {

	@RequestMapping(value = "/contato/novo")
	public ModelAndView novo() {
		
		ModelAndView mv = new ModelAndView("site/contato/Contato");
		return mv;
		
	}
}
