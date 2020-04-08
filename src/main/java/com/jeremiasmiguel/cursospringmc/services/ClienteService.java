package com.jeremiasmiguel.cursospringmc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente find(Integer id) {
		 Optional<Cliente> objetoCliente = clienteRepository.findById(id);
		return objetoCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())
		);
	} 
}
