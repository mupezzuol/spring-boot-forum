package br.com.pezzuka.forum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTrelloAPI {

	@Test
	public void consumindoApiTrello() {
		
		RestTemplate restTemplate = new RestTemplate(); 
	
		// URI (GET) ->>> https://api.trello.com/1/boards/5612e4f91b25c15e873722b8?fields=all
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.trello.com")
				.path("1/boards/5612e4f91b25c15e873722b8")
				.queryParam("fields", "all")//name + value
				.build();
		
		
		//Entity é os dados retornados da consulta da API.. Só irá nos retorna aquilo que está na classe modelo utilizada, nesse caso Trello
		//entity -> Pegamos Header, Body, etc....
		ResponseEntity<Trello> entity = restTemplate.getForEntity(uri.toUriString(), Trello.class);
		
		imprimindoResultado(entity);
		
	}

	
	private void imprimindoResultado(ResponseEntity<Trello> entity) {
		System.out.println("=========================== INICIO - CONSUMINDO API DO TRELLO");
		
		System.out.println("Nome: "+entity.getBody().getId());
		System.out.println("Nome: "+entity.getBody().getName());
		System.out.println("Nome: "+entity.getBody().getDesc());
		System.out.println("Nome: "+entity.getBody().getIddescData());
		System.out.println("Nome: "+entity.getBody().getClosed());
		System.out.println("Nome: "+entity.getBody().getIdOrganization());
		
		System.out.println("=========================== FIM");
	}
	
	
}
