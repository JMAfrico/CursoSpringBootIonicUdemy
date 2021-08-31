package com.jmarcostech.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.jmarcostech.cursomc.domain.Cliente;
import com.jmarcostech.cursomc.dto.ClienteDTO;
import com.jmarcostech.cursomc.repositories.ClienteRepository;
import com.jmarcostech.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	//Metodo para executar requisicao e utilizar o que está no URI
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	ClienteRepository clienterepo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		//Método busca o atributo da variavel que está na URI e envia para a Coleção MAP, 
		//que captura o valor e passa pra uma variável, a fim de poder verificar o Update daquele ID
		@SuppressWarnings("unchecked")
		Map<String,String> map= (Map<String,String> )request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		//Inicializa uma lista de erros personalizadas
		List<FieldMessage> list = new ArrayList<>();
		
		// inclua os testes aqui, inserindo erros na lista
		//Se o email for diferente de nulo e não for diferente de algum email existente no banco
		Cliente aux = clienterepo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existe cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
