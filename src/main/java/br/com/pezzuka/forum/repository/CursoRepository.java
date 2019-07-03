package br.com.pezzuka.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pezzuka.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
