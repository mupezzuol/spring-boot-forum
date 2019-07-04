package br.com.pezzuka.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // -> Habilitando o Web Security
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	
	
	//Configurações de Autenticação (controle de acesso, login etc...)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	}
	
	
	//Configurações de Autorização (URL, quem pode acessar a URL, perfil de acesso etc...)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll();
	}
	
	
	//Configurações de Recursos Estáticos (Acesso a CSS, JavaScript, Imagens etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	
}
