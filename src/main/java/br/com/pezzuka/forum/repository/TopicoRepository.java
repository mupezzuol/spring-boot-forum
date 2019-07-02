package br.com.pezzuka.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.pezzuka.forum.model.Topico;

//JpaRepository -> Parametros -> 1º Entidade 2º Tipo do ID da entidade 
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	
}
