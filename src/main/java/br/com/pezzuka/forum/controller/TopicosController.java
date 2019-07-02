package br.com.pezzuka.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pezzuka.forum.dto.TopicoDTO;
import br.com.pezzuka.forum.repository.TopicoRepository;

//Usando o RestController ele já assumi que todo retorno do método será um '@ResponseBody'
@RestController
public class TopicosController {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@RequestMapping("/topicos") 
	//URI com parametro: /topicos?nomeCurso=Murillo
	public List<TopicoDTO> lista(String nomeCurso){
		
		if (nomeCurso == null) {
			return TopicoDTO.converter(topicoRepository.findAll());
		}else {
			//URL com Filtro -> /topicos?nomeCurso=Spring+Boot (quando temos espaço, usamos + na url)
			return TopicoDTO.converter(topicoRepository.findByCursoNome(nomeCurso));
		}
		
	}
	
	
	
	
}
