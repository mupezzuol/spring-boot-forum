package br.com.pezzuka.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.pezzuka.forum.model.Curso;
import br.com.pezzuka.forum.model.Topico;
import br.com.pezzuka.forum.repository.CursoRepository;

public class TopicoForm {
	
	//Bean Validation faz integração com Spring (validando os dados que virão do form)
	//Para ser chamado essas validações é preciso utilizar o '@Valid' 
	@NotNull @NotEmpty @Length(min = 5)
	private String titulo;
	
	@NotNull @NotEmpty
	private String mensagem;
	
	@NotNull @NotEmpty
	private String nomeCurso;
	
	//Converter TopicoForm TO Topico
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
}
