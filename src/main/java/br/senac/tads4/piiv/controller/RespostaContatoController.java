package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.model.enumerated.TipoContato;
import br.senac.tads4.piiv.repository.ContatoRepository;
import br.senac.tads4.piiv.repository.filter.ContatoFilter;
import br.senac.tads4.piiv.service.ContatoService;

@Controller
@RequestMapping(value = "/admin/contatos")
public class RespostaContatoController {

	@Autowired
	private ContatoService contatoService;

	@Autowired
	private ContatoRepository contatoRepository;

	/**
	 * Exibe a lista de contato
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pesquisar")
	public ModelAndView novo(ContatoFilter contatoFilter, BindingResult result) {
		ModelAndView mv = new ModelAndView("backoffice/contato/ListaDeContato");
		mv.addObject("tiposContato", TipoContato.values());
		mv.addObject("contatos", contatoRepository.filtrar(contatoFilter));
		return mv;
	}

	/**
	 * Exibe um contato conforme o id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/resposta/{id}")
	public ModelAndView respostaContato(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("backoffice/contato/RespostaContato");
		mv.addObject("contato", contatoRepository.findOne(id));
		mv.addObject("tiposContato", TipoContato.values());
		return mv;
	}

	/**
	 * Método responsável por cuidar da submissão do formulário de resposta de contato
	 * 
	 * @param contato
	 * @param result
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/resposta", method = RequestMethod.POST)
	public ModelAndView respota(@Valid Contato contato, BindingResult result, RedirectAttributes attributes) {
		if (StringUtils.isEmpty(contato.getResposta())) {
			attributes.addFlashAttribute("erroCampoResposta", "O campo resposta é obrigatório");
			return new ModelAndView("redirect:/admin/contatos/resposta/" + contato.getId());
		}

		try {
			contatoService.alterar(contato);
		} catch (Exception e) {
			return respostaContato(contato.getId());
		}

		attributes.addFlashAttribute("mensagem", "Contato respondido com sucesso");
		return new ModelAndView("redirect:/admin/contatos/pesquisar");
	}
}
