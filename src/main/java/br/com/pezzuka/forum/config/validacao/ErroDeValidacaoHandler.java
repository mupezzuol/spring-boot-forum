package br.com.pezzuka.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

	//Captura mensagem de erro das Exception, trata a questão de internacionalização
	@Autowired
	private MessageSource messageSource;
	
	
	//Quando ocorrer uma Exception 'MethodArgumentNotValidException' em qualquer RestController da minha aplicação, esse método será capturado
	//Qnd eu intercepto minha Exception ele retorna 200 como padrão, porém estou forçando para que ele continue retornando Status 400
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException exception) {
		
		List<ErroDeFormularioDTO> dto = new ArrayList<>();
		
		//A Exception recupera quais campos/validações deram erro
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			//Passo a Exception + Locale que trata o idioma da mensagem etc...
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;//Lista dos erros encontrados
	}
	
	
	
}
