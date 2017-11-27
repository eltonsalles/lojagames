package br.senac.tads4.piiv.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.senac.tads4.piiv.security.UsuarioSistema;

@Controller
@RequestMapping("/")
public class LoginController {

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

			return new ModelAndView("redirect:/clientes/conta/index");
		}

		ModelAndView mv = new ModelAndView("site/Login");
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
			return new ModelAndView("redirect:/admin/usuarios/novo");
		}

		ModelAndView mv = new ModelAndView("backoffice/Login");
		return mv;
	}

	/**
	 * Página exibida quando o usuário não tem permissão de acesso
	 * 
	 * @return
	 */
	@GetMapping("403")
	public ModelAndView acessoNegado() {
		return new ModelAndView("backoffice/error/403");
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
