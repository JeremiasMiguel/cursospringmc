package com.jeremiasmiguel.cursospringmc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.dto.ClienteDTO;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.resources.exceptions.FieldMessage;

// Definindo o nome da Interface que manuseará o Validator (ClienteUpdate)
// Definindo que a classe ClienteNewDTO é o tipo/classe que será validado
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext context) {
		
		// Mapeando por meio da requisição HTTP a URI presente no objeto que se deseja atualizar (clientes/URI)
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		// Buscando o atributo ID da URI que foi mapeada
		Integer uriID = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		// Verificando se o email já existe (para não ocorrer duplicação).
		// Nesse caso, é necessário verificar se o email que for igual não é o do próprio cliente que se deseja atualizar,
		// o email duplicado deve ser de outro cliente que não seja o próprio
		Cliente clienteAuxiliar = clienteRepository.findByEmail(clienteDTO.getEmail());
		// Verificando se o ID buscado é igual ao que foi apontado, se for, é porque outro cliente já tem esse email
		if(clienteAuxiliar != null && !clienteAuxiliar.getId().equals(uriID)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			// Acrescentando na lista de erros do Framework, os erros da validação personalizada
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
