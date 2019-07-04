package br.com.pezzuka.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // -> Habilitando o Web Security
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	
}
