package br.com.pezzuka.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pezzuka.forum.config.security.TokenService;
import br.com.pezzuka.forum.form.LoginForm;

//Controller de Login
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	//Spring não faz injeção de dependencia desse cara, é preciso configura-lo
	//Nós configuramos na classe 'SecurityConfiguration' sobrescrevendo o método 'authenticationManager()'
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid LoginForm form){
		
		//Convertemos os dados do Form para um OBJ do Spring de Autentição
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		try {
			//Quando cair aqui será chamado as configs de 'AutenticacaoService' onde ele verifica o email/senha no banco (classe 'AutenticacaoService')
			Authentication authenticate = authManager.authenticate(dadosLogin);
			
			//Geramos nosso Token
			String token = tokenService.gerarToken(authenticate);
			
			System.out.println(token);
			
			return ResponseEntity.ok().build();//OK -> Login autenticado com sucesso
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();//404
		}
		
	}
	
	
}
