package br.senac.tads4.piiv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.repository.TipoConsoleRepository;
import br.senac.tads4.piiv.security.UsuarioSistema;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@Autowired
	private TipoConsoleRepository tipoConsoleRepository;

	/**
	 * Login para cliente
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("login/site")
	public ModelAndView loginSite(@AuthenticationPrincipal User user, HttpSession session) {
		if (user != null) {
			UsuarioSistema usuarioSistema = this.recuperarUsuario();
			session.setAttribute("usuarioLogado", usuarioSistema);

			if (session.getAttribute("carrinhoCompras") != null && (Integer) session.getAttribute("carrinhoCompras") > 0) {
				return new ModelAndView("redirect:/carrinho-compra/finalizar-compra");
			}
			return new ModelAndView("redirect:/clientes/conta/index");
		}

		ModelAndView mv = new ModelAndView("site/Login");
		mv.addObject("tiposConsole", tipoConsoleRepository.findAll());
		return mv;
	}

	/**
	 * Login para backoffice
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("admin/login/backoffice")
	public ModelAndView loginBackoffice(@AuthenticationPrincipal User user) {
		if (user != null) {
			return new ModelAndView("redirect:/admin");
		}

		ModelAndView mv = new ModelAndView("backoffice/Login");
		return mv;
	}

	/**
	 * Recupera o usuário logado
	 * 
	 * @return
	 */
	public UsuarioSistema recuperarUsuario() {
		return (UsuarioSistema) ((SecurityContext) SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
	}
}
