package br.com.pezzuka.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@RequestMapping("/")
	@ResponseBody //Coloco isso, senão o Spring irá entender que o String de retorno está apontando para uma página
	public String helloWorl() {
		return "Hello World";
	}
	
	
}
