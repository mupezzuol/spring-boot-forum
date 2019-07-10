package br.com.pezzuka.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pezzuka.forum.model.Usuario;
import br.com.pezzuka.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	
	//Recebo no Construtor algumas injeções de dependencia que veio de outras classes, pois nos Filter não pode ser feito isso
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		super();
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		boolean valido = tokenService.isValidoToken(token);
		
	
		if(valido) {
			//Forçando a autenticação do Cliente pro Spring, pois já validamos o Token
			autenticarCliente(token);
		}
		
		//Após validar tudo, eu aviso o Spring que pode continuar o fluxo normal...
		filterChain.doFilter(request, response);
	}
	

	
	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		
		//Passo o Usuário + Senha + Perfis
		//getAuthorities() -> Perfis de acesso
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		
		//Aqui eu forço a autenticação
		SecurityContextHolder.getContext().setAuthentication(authentication);
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
