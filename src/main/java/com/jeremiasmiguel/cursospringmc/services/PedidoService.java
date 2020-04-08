package com.jeremiasmiguel.cursospringmc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Pedido;
import com.jeremiasmiguel.cursospringmc.repositories.PedidoRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido find(Integer id) {
		 Optional<Pedido> objetoPedido = pedidoRepository.findById(id);
		return objetoPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())
		);
	} 
}
