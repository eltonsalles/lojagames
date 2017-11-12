package br.senac.tads4.piiv.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/site")
	public ModelAndView loginSite() {
		ModelAndView mv = new ModelAndView("site/login/Login");
		return mv;
	}
	
	@RequestMapping("/backoffice")
	public ModelAndView loginBackoffice(@AuthenticationPrincipal User user) {
		if (user != null) {
			return new ModelAndView("redirect:/usuarios/novo");
		}
		
		ModelAndView mv = new ModelAndView("backoffice/Login");
		return mv;
	}
}
