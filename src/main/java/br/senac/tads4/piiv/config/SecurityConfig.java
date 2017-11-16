package br.senac.tads4.piiv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}
	
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
			.antMatchers("/clientes/**") // o correto é: /clientes/novo
			.antMatchers("/pedidos/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/produtos/consoles/novo").hasRole("CADASTRAR_PRODUTO")
				.antMatchers("/produtos/controles/novo").hasRole("CADASTRAR_PRODUTO")
				.antMatchers("/produtos/jogos/novo").hasRole("CADASTRAR_PRODUTO")
				.antMatchers("/produtos/pesquisar").hasRole("CONSULTAR_PRODUTO")
				.antMatchers("/usuarios/novo").hasRole("CADASTRAR_USUARIO")
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login/backoffice")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			.exceptionHandling()
				.accessDeniedPage("/403"); //.and().sessionManagement().invalidSessionUrl("/"); Sessão inválida
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
