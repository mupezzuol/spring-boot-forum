package br.com.pezzuka.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pezzuka.forum.controller.form.AtualizacaoTopicoForm;
import br.com.pezzuka.forum.controller.form.TopicoForm;
import br.com.pezzuka.forum.dto.DetalhesTopicoDTO;
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
	 
	
	//URI com parametro com ?: /topicos?nomeCurso=Murillo (nesse caso não precisa do PathVariable)
	//@RequestParam -> digo para o Spring que os dados virão através de parametro da URL, conforme acima
	//Exemplo com paginação: /topicos?pagina=0&qtd=2 (Quem consome API que decide o esquema de paginação que será feito)
	//Se você não passar a pagina e qtd dará erro, pois eles são obrigatório
	@GetMapping
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso, @RequestParam int pagina, 
			@RequestParam int qtd, String ordenacao){
		
		//Crio uma paginação, de acordo com a página e quantidade
		//Alguns Métodos da JPARepository já aceitam Paginação
		//Ordenação passando o Direction (crescente ou decrescente) + varargs dos campos de ordenação
		Pageable paginacao = new PageRequest(pagina, qtd, Direction.DESC, ordenacao);
		
		if (nomeCurso == null) {
			return TopicoDTO.converter(topicoRepository.findAll(paginacao));
		}else {
			//URL com Filtro -> /topicos?nomeCurso=Spring+Boot (quando temos espaço, usamos + na url)
			return TopicoDTO.converter(topicoRepository.findByCursoNome(nomeCurso, paginacao));
		}
	}
	
	
	@GetMapping("/{id}")//URL pronta -> /topicos/2 (id dinamico).. PathVariable já entende o param, pois está com o mesmo nome
	public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {
		//Retorna 1 Optional, porém se deu erro não terá dado, não joga Exception no cliente
	 	Optional<Topico> topico = topicoRepository.findById(id);
	 	
	 	//Se o Optinal tiver presente, tiver o Topico ele retorna o Detalhe
	 	if(topico.isPresent()) {
	 		return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));//Usamos o .get() para pegar o Topico que está dentro do Optional
	 	}
	 	
	 	//Se não retornou nada, forçamos um retorno de 404
	 	return ResponseEntity.notFound().build();//Retorna 404
	}
	
	
	@PutMapping("/{id}")
	@Transactional //Avisa o Spring a fazer o Commit no final da transação, assim ele faz o update do registro...
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		
	 	if(optional.isPresent()) {
	 		//Não preciso chamar o atualização do JpaRepository, pois ao sair do método da Controller ele fará o update automático no banco
			Topico topico = form.atualizar(id, topicoRepository);
	 		return ResponseEntity.ok(new TopicoDTO(topico));
	 	}
		
	 	return ResponseEntity.notFound().build();//Retorna 404
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();//Retorna 200
		}
		
		return ResponseEntity.notFound().build();//Retorna 404
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
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	
	
}
