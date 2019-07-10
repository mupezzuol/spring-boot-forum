package br.com.pezzuka.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pezzuka.forum.model.Usuario;
import br.com.pezzuka.forum.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> user = usuarioRepository.findByEmail(username);
		
		if(user.isPresent()) {
			//Pego Usuário no Optional se tiver OK 
			//Estando OK o Spring válida a senha automáticamente
			return user.get();
		}
		
		throw new UsernameNotFoundException("Login ou Senha inválidos!");
	}
	
	
}
