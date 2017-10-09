package br.senac.tads4.piiv.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.senac.tads4.piiv.service.ClienteService;

/**
 * Classe de configuração do pacote service
 * 
 * @author Elton
 *
 */
@Configuration
@ComponentScan(basePackageClasses = ClienteService.class)
public class ServiceConfig {

}
