package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Usuario;
import br.senac.tads4.piiv.repository.GrupoRepository;
import br.senac.tads4.piiv.repository.UsuarioRepository;
import br.senac.tads4.piiv.repository.filter.UsuarioFilter;
import br.senac.tads4.piiv.service.UsuarioService;
import br.senac.tads4.piiv.service.exception.EmailUsuarioJaCadastradoException;
import br.senac.tads4.piiv.service.exception.SenhaUsuarioObrigatoriaException;

@Controller
@RequestMapping(value = "/admin/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private GrupoRepository grupoRepository;

	@RequestMapping(value = "/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("backoffice/usuario/CadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}

		try {
			usuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		} catch (SenhaUsuarioObrigatoriaException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}

		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return new ModelAndView("redirect:/admin/usuarios/novo");
	}

	/**
	 * Exibe o formulário para a pesquisa de usuários
	 * 
	 * @param usuarioFilter
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/pesquisar")
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/usuario/PesquisarUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		mv.addObject("usuarios", usuarioRepository.filtrar(usuarioFilter));
		return mv;
	}

	/**
	 * Método para exibir os dados do usuário para alteração
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		usuario.setSenha(null);

		ModelAndView mv = new ModelAndView("backoffice/usuario/CadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		mv.addObject("usuario", usuario);
		return mv;
	}

	/**
	 * Método usado caso existam erros no formulário de alteração
	 * 
	 * @param usuario
	 * @return
	 */
	private ModelAndView alterar(Usuario usuario) {
		ModelAndView mv = new ModelAndView("backoffice/usuario/CadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		return mv;
	}

	/**
	 * Método que recebe a submissão do formulário de alteração de usuário
	 * 
	 * @param usuario
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/alterar", method = RequestMethod.POST)
	public ModelAndView alterar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return this.alterar(usuario);
		}

		try {
			usuarioService.alterar(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("id", e.getMessage(), e.getMessage());
			return novo(usuario);
		}

		attributes.addFlashAttribute("mensagem", "Usuário alterado com sucesso!");
		return new ModelAndView("redirect:/admin/usuarios/pesquisar");
	}

	/**
	 * Método para excluir
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/excluir/{id}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			usuarioService.excluir(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok().build();
	}
}
