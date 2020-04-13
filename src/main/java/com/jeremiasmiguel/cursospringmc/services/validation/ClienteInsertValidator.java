package com.jeremiasmiguel.cursospringmc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.domain.enums.TipoCliente;
import com.jeremiasmiguel.cursospringmc.dto.ClienteNewDTO;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.resources.exceptions.FieldMessage;
import com.jeremiasmiguel.cursospringmc.services.validation.utils.BR;

// Definindo o nome da Interface que manuseará o Validator (ClienteInsert)
// Definindo que a classe ClienteNewDTO é o tipo/classe que será validado
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// Se o TipoCliente for Pessoa Física, verifica se o CPF é inválido, se for, adiciona-se um erro de validação com FieldMessage
		if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		// Se o TipoCliente for Pessoa Jurídica, verifica se o CNPJ é inválido, se for, adiciona-se um erro de validação com FieldMessage
		if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		// Verificando se o email já existe (para não ocorrer duplicação)
		Cliente clienteAuxiliar = clienteRepository.findByEmail(clienteNewDTO.getEmail());
		if(clienteAuxiliar != null) {
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
