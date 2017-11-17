package br.senac.tads4.piiv.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

	@RequestMapping("login/site")
	public ModelAndView loginSite(@AuthenticationPrincipal User user) {
		if (user != null) {
			return new ModelAndView("redirect:/clientes/conta/index/1");
		}
		
		ModelAndView mv = new ModelAndView("site/Login");
		return mv;
	}
	
	@RequestMapping("login/backoffice")
	public ModelAndView loginBackoffice(@AuthenticationPrincipal User user) {
		if (user != null) {
			return new ModelAndView("redirect:/usuarios/novo");
		}
		
		ModelAndView mv = new ModelAndView("backoffice/Login");
		return mv;
	}
	
	@GetMapping("403")
	public ModelAndView acessoNegado() {
		return new ModelAndView("backoffice/error/403");
	}
}
