package br.senac.tads4.piiv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.senac.tads4.piiv.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;
	
	/**
	 * Método usado pelo spring security para autenticar o usuário
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		
		/**
		 * Urls que são ignoradas pelo spring security
		 */
		@Override
		public void configure(WebSecurity web) throws Exception {
			web
				.ignoring()
				.antMatchers("/images/**")
				.antMatchers("/javascripts/**")
				.antMatchers("/layout/**")
				.antMatchers("/stylesheets/**")
				.antMatchers("/imagens/**")
				.antMatchers("/")
				.antMatchers("/detalhes-do-produto/**")
				.antMatchers("/lista-produto/**")
				.antMatchers("/carrinho-compra/**")
				.antMatchers("/clientes/novo")
				.antMatchers("/contatos/novo");
		}
		
		/**
		 * Configuração para urls do backoffice. Todas as requisições que a url comece com admin serão tratadas aqui
		 * pelo spring security
		 */
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/admin/**")
				.authorizeRequests()
					.antMatchers("/admin/produtos/consoles/novo").hasRole("CADASTRAR_PRODUTO")
					.antMatchers("/admin/produtos/controles/novo").hasRole("CADASTRAR_PRODUTO")
					.antMatchers("/admin/produtos/jogos/novo").hasRole("CADASTRAR_PRODUTO")
					.antMatchers("/admin/produtos/consoles/alterar/**").hasRole("ALTERAR_PRODUTO")
					.antMatchers("/admin/produtos/controles/alterar/**").hasRole("ALTERAR_PRODUTO")
					.antMatchers("/admin/produtos/jogos/alterar/**").hasRole("ALTERAR_PRODUTO")
					.antMatchers("/admin/produtos/pesquisar").hasRole("CONSULTAR_PRODUTO")
					.antMatchers("/admin/usuarios/novo").hasRole("CADASTRAR_USUARIO")
					.antMatchers("/admin/contatos/resposta/**").hasRole("RESPONDER_CONTATO")
					.antMatchers("/admin/contatos/pesquisar").hasRole("CONSULTAR_CONTATO")
					.antMatchers("/admin/descontos/**").hasRole("CADASTRAR_DESCONTO")
					.antMatchers("/admin/pedidos/**").hasRole("LIBERAR_PEDIDO")
					.antMatchers("/admin/relatorio/vendas").hasRole("CONSULTAR_VENDA")
					.antMatchers("/admin/produtos/historico").hasRole("CONSULTAR_HISTORICO")
					.antMatchers("/admin/produtos/movimentar").hasRole("CADASTRAR_MOVIMENTACAO")
					.anyRequest().hasRole("ADMIN")
				.and()
					.formLogin()
					.loginPage("/admin/login/backoffice")
					.permitAll()
				.and()
					.logout()
					.logoutUrl("/admin/logout")
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
				.and()
					.exceptionHandling()
						.accessDeniedPage("/403");
		}
	}

	@Configuration
	@Order(2)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		/**
		 * Configuração para as demais urls (cliente)
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/clientes/**").hasRole("CLIENTE")
					.antMatchers("/pedidos/**").hasRole("CLIENTE")
					.antMatchers("/boleto/**").hasRole("CLIENTE")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login/site")
					.permitAll()
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
					.exceptionHandling()
						.accessDeniedPage("/403");
		}
	}
	
	/**
	 * Método usado para retornar uma instância do BCrypt
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
