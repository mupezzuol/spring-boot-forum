package br.com.pezzuka.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pezzuka.forum.dto.TopicoDTO;
import br.com.pezzuka.forum.model.Curso;
import br.com.pezzuka.forum.model.Topico;

//Usando o RestController ele já assumi que todo retorno do método será um '@ResponseBody'
@RestController
public class TopicosController {
	
	@RequestMapping("/topicos")
	//@ResponseBody -> Não preciso utilizar pois estou usando o RestController que automaticamente já nos retorna um ResponseBody 
	public List<TopicoDTO> lista(){
		Topico t = new Topico("Dúvida", "Dúvida com Spring", new Curso("Spring", "Programação"));
		Topico t2 = new Topico("Dúvida 2", "Dúvida com Java 8", new Curso("Java", "Programação"));
		
		return TopicoDTO.converter(Arrays.asList(t, t2, t, t2));
	}
	
	
	
	
}
