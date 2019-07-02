package br.com.pezzuka.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.pezzuka.forum.model.Topico;

//JpaRepository -> Parametros -> 1º Entidade 2º Tipo do ID da entidade 
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//Filtro -> Dentro da entidade Topico, eu tenho um atributo 'Curso' que é uma classe, dentro da classe 'Curso' eu tenho um atributo 'nome'
	//Nesse exemplo estamos filtrando atráves de um atributo que está dentro de uma classe que está sendo relacionada dentro da nossa Entidade
	//Caso você tenha uma tributo na classe Topico que se chama 'cursoNome' você deverá avisar o spring para tratar essa igualdade
	//Tratando ambiguidade -> findByCurso_Nome (utilizando o _ você indica que o Curso é um Entidade e após o _ é o atributo dentro dele)
	List<Topico> findByCursoNome(String nomeCurso);

	
	//Para criar métodos personalizado, com nome na assinatura diferente do disponibilizados nós devemos criar a Query
	//A Query é baseada em JPQL, porém há possibilidade de executar querys nativas.. 
	//no where na verdade é uma navegação entre as entidades (Topico -> Curso -> Nome)
	//Devemos usar o @Param para identificar o parametro da query
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
	
	
	
}
