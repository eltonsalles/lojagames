package br.senac.tads4.piiv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.senac.tads4.piiv.service.ClienteService;
import br.senac.tads4.piiv.storage.ImagemStorage;
import br.senac.tads4.piiv.storage.local.ImagemStorageLocal;

/**
 * Classe de configuração do pacote service
 * 
 * @author Elton
 *
 */
@Configuration
@ComponentScan(basePackageClasses = ClienteService.class)
public class ServiceConfig {

	@Bean
	public ImagemStorage ImagemStorage() {
		return new ImagemStorageLocal();
	}
}
