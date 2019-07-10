package br.com.pezzuka.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.pezzuka.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	//Pegando valores que estão setados no arquivo de configuração 'application.properties'
	@Value("${forum.jwt.expiration}")
	private String expiration;//Tempo em millisegundos para 1 dia (24h)
	
	@Value("${forum.jwt.secret}")
	private String secret;//Senha para o algoritmo de encriptação

	
	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		
		//Pego data de hoje em milisegundos + os milisegundos setados (essa será a data de expiração, data atual + 24h)
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Murillo")//Quem é que está gerando esse Token, qual é a aplicação
				.setSubject(logado.getId().toString())//Quem é o usuário, passamos o Id único que identifica quem é esse usuário
				.setIssuedAt(hoje)//Qual a data da geração do Token
				.setExpiration(dataExpiracao)//Quando data de expiração (igual uma sessão tradicional)
				.signWith(SignatureAlgorithm.HS256, secret)//Como iremos criptografar nosso Token (Algoritmo de encriptação + a senha)
				.compact();//Retorna nosso Token
	}

	
}
