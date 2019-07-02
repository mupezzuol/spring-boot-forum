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
	//@ResponseBody -> Não preciso utilizar pois estou usando o RestController que automaticamente já nos retorna um ResponseBody 
	public List<TopicoDTO> lista(){
		return TopicoDTO.converter(topicoRepository.findAll());
	}
	
	
	
	
}
