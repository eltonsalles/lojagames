package br.senac.tads4.piiv.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.TipoConsole;

public class TipoConsoleConverter implements Converter<String, TipoConsole> {

	@Override
	public TipoConsole convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			TipoConsole tipoConsole = new TipoConsole();
			tipoConsole.setId(Long.valueOf(id));
			return tipoConsole;
		}
		
		return null;
	}
}
