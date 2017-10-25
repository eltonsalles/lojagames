package br.senac.tads4.piiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping (value = "/produtos")
public class ProdutoController {
	
	@RequestMapping(value = "/pesquisar")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("backoffice/produto/PesquisarEstoque");
		return mv;
	}
}
