package br.senac.tads4.piiv.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageBackofficeElementTagProcessor extends AbstractElementTagProcessor {

	private static final String NOME_TAG = "messagebackoffice";
	private static final int PRECEDENCIA = 1000;
	
	public MessageBackofficeElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, true, PRECEDENCIA);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		
		IModel model = modelFactory.createModel();
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:replace", "layout/backoffice/fragments/MensagemSucesso"));
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:replace", "layout/backoffice/fragments/MensagensErroValidacao"));
		
		structureHandler.replaceWith(model, true);
	}
}
