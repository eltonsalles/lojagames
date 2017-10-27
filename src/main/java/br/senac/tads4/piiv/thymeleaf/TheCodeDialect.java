package br.senac.tads4.piiv.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.senac.tads4.piiv.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.senac.tads4.piiv.thymeleaf.processor.FormImagemProdutoElementTagProcessor;
import br.senac.tads4.piiv.thymeleaf.processor.FormProdutoElementTagProcessor;
import br.senac.tads4.piiv.thymeleaf.processor.MessageBackofficeElementTagProcessor;
import br.senac.tads4.piiv.thymeleaf.processor.MessageSiteElementTagProcessor;

public class TheCodeDialect extends AbstractProcessorDialect {

	public TheCodeDialect() {
		super("Loja de Games TheCode", "thecode", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new FormProdutoElementTagProcessor(dialectPrefix));
		processadores.add(new FormImagemProdutoElementTagProcessor(dialectPrefix));
		processadores.add(new MessageBackofficeElementTagProcessor(dialectPrefix));
		processadores.add(new MessageSiteElementTagProcessor(dialectPrefix));
		return processadores;
	}
}
