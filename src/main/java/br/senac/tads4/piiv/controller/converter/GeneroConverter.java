package br.senac.tads4.piiv.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.senac.tads4.piiv.model.Genero;

public class GeneroConverter implements Converter<String, Genero> {

	@Override
	public Genero convert(String id) {
		if (!StringUtils.isEmpty(id)) {
			Genero genero = new Genero();
			genero.setId(Long.valueOf(id));
			return genero;
		}
		
		return null;
	}
}
