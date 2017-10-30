package br.senac.tads4.piiv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {
	
	/**
	@RequestMapping("/login")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("site/login/Login");
		return mv;
	}
	**/
	
	@RequestMapping("/finalizar-compra")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("site/compra/FinalizarCompra");
		return mv;
	}
}
