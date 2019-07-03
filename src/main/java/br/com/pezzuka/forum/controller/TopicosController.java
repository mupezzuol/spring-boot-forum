package br.com.pezzuka.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pezzuka.forum.controller.form.TopicoForm;
import br.com.pezzuka.forum.dto.TopicoDTO;
import br.com.pezzuka.forum.model.Topico;
import br.com.pezzuka.forum.repository.CursoRepository;
import br.com.pezzuka.forum.repository.TopicoRepository;

//Usando o RestController ele já assumi que todo retorno do método será um '@ResponseBody'
@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	TopicoRepository topicoRepository;
	
	@Autowired
	CursoRepository cursoRepository;
	 
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso){//URI com parametro: /topicos?nomeCurso=Murillo
		
		if (nomeCurso == null) {
			return TopicoDTO.converter(topicoRepository.findAll());
		}else {
			//URL com Filtro -> /topicos?nomeCurso=Spring+Boot (quando temos espaço, usamos + na url)
			return TopicoDTO.converter(topicoRepository.findByCursoNome(nomeCurso));
		}
	}
	
	
	//@Valid -> Digo para ele rodar as validações do Bean Validation adicionadas na classe 'TopicoForm' através das tags (@NotNull, @NotEmpty etc...)
	//Se o @Valid encontrar algum erro de validação o método nem executa e devolve um -> Status: 400 Bad Request
	//ResponseEntity -> Usamos para retornamos os códigos de sucesso, criado etc.. 200, 201 etc...
	//ResponseEntity.created -> É quando retornamos código 201... Porém 201 devemos retornar duas coisas
	//1º CABEÇALHO (location, com a URI desse novo recurso)
	//2º CORPO devolvo uma representação desse recurso criado
	//Passamos a URI, que nós construimos através da injeção do Spring + a chamada para um método de busca por ID
	//created -> Nós chamamos a URI que está os endereços de consulta + o recurso que acabmos de criar no Body, porém usando o DTO
	@PostMapping
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	
	
	
}
