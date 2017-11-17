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
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder());
	}

	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
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
				.antMatchers("/clientes/novo");
		}
		
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/clientes/**")
				.authorizeRequests()
					.anyRequest().hasRole("CLIENTE")
					.and()
				.httpBasic();
		}
	}

	@Configuration
	@Order(2)
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
					.antMatchers("/produtos/consoles/novo").hasRole("CADASTRAR_PRODUTO")
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
					.accessDeniedPage("/403");
		}
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
