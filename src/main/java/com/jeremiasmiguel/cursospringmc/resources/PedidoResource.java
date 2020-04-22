package com.jeremiasmiguel.cursospringmc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jeremiasmiguel.cursospringmc.domain.Pedido;
import com.jeremiasmiguel.cursospringmc.services.PedidoService;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private PedidoService pedidoService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		Pedido objetoPedido = pedidoService.find(id);
		return ResponseEntity.ok().body(objetoPedido);
	}
	
	// Não foi criado um DTO para o pedido porque há muitos dados associados
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) { 
		// Valid -> Para que o objeto seja validado antes de ser manipulado | RequestBody -> Faz que o json se converta em objeto Java
		pedido = pedidoService.insert(pedido);
		// Definindo o endpoint e retornando a resposta da entidade de acordo com a resposta do sistema (URI), seguindo
		// as boas práticas da arquitetura REST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	// Criando endpoint que permite ao cliente buscar somente os seus pedidos
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="instante") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) { 
		Page<Pedido> listaPedidos = pedidoService.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(listaPedidos);
	}
}
