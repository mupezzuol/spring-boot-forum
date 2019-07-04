package br.com.pezzuka.forum.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import br.com.pezzuka.forum.model.enums.PerfilEnum;

@Entity
public class Perfil implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(value = EnumType.STRING)
	private PerfilEnum nome;
	
	
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PerfilEnum getNome() {
		return nome;
	}
	public void setNome(PerfilEnum nome) {
		this.nome = nome;
	}

}
