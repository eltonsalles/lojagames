package br.senac.tads4.piiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senac.tads4.piiv.model.Contato;
import br.senac.tads4.piiv.repository.ContatoRepository;
import br.senac.tads4.piiv.service.ContatoService;

@Controller
@RequestMapping(value = "/admin/contato")
public class RespostaContatoController {

	@Autowired
	private ContatoService contatoService;
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@RequestMapping(value = "pesquisa/contato")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("backoffice/contato/listaDeContato");
		mv.addObject("contatos", contatoRepository.findAll());
		return mv;
	}
	
	@RequestMapping(value = "resposta/contato/{id}")
	public ModelAndView respostaContato(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("backoffice/contato/RespostaContato");
		mv.addObject("contato", contatoRepository.findOne(id));
		return mv;
	}
	
	@RequestMapping(value = "resposta/contato", method = RequestMethod.POST)
	public ModelAndView respota(@Valid Contato contato, BindingResult result, RedirectAttributes attributes) {
//		if (result.hasErrors()) {
//			return respostaContato(contato.getId());
//		}
		
		System.out.println(">>>>>>>>>>");
		
		try {
			contatoService.salvar(contato);
		} catch (Exception e) { // Trocar por uma exceção mais especifica
			// result.rejectValue();
			return respostaContato(contato.getId());
		}
		
		attributes.addFlashAttribute("messagem", "Contato respondido com sucesso");
		return new ModelAndView("redirect:/pesquisa/contato");
	}
}
