package br.com.pezzuka.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // -> Habilitando o Web Security
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	AutenticacaoService autenticacaoService;//Implementa um 'UserDetailsService'
	
	
	//Configurações de Autenticação (controle de acesso, login etc...)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//passwordEncoder(new BCryptPasswordEncoder() -> Adiciona a forma de encriptação da senha
		//userDetailsService(xxx) -> Indico qual meu UserDetails, configs, login etc....
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	//Configurações de Autorização (URL, quem pode acessar a URL, perfil de acesso etc...)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/topicos").permitAll()
			.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
			.anyRequest().authenticated()//Para as outras URL ele deverá estar autenticado
			.and().formLogin();//Formulário padrão de Login do Spring Security
	}
	
	
	//Configurações de Recursos Estáticos (Acesso a CSS, JavaScript, Imagens etc...)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
	
}
