package br.com.pezzuka.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	//Recebo no Construtor meu TokenSerive instanciado (Autowride) de quem chama-lo
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		super();
		this.tokenService = tokenService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isValidoToken(token);
		
		System.out.println(valido);
		
		//Após validar tudo, eu aviso o Spring que pode continuar o fluxo normal
		filterChain.doFilter(request, response);
	}
	

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");//Passamos no Header da requisição via Postman etc...
		
		//Validando se veio vazio o value do Header, se não começa no formato correto etc...
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		//Retorno somente o valor do Token que vai da posição 7 até a última
		return token.substring(7, token.length());
	}
	
	/*
	->>> ENVIO DO TOKEN NA REQUISIÇÃO:
	
	Key: Authorization
	Value: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZG8gRsOzcnVtIGRhIE11cmlsbG8iLCJzdWIiOiIxIiwiaWF0IjoxNTYyNzgzMDY1LCJleHAiOjE1NjI4Njk0NjV9.8SQlcykIC9qLehH6asNO41yVQcSYsS9mLFwzI6Zch9s
	
	Obs: O Valor é o TIPO + TOKEN, separando-os com espaço
	*/

}
