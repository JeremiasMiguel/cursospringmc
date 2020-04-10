package com.jeremiasmiguel.cursospringmc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.dto.ClienteDTO;
import com.jeremiasmiguel.cursospringmc.dto.ClienteNewDTO;
import com.jeremiasmiguel.cursospringmc.services.ClienteService;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		Cliente objetoCliente= clienteService.find(id);
		return ResponseEntity.ok().body(objetoCliente);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO) { 
		// Valid -> Para que o objeto DTO seja validado antes de ser manipulado | RequestBody -> Faz que o json se converta em objeto Java
		// Converte Cliente em DTO
		Cliente cliente = this.clienteService.fromDTO(clienteNewDTO);
		cliente = clienteService.insert(cliente);
		// Definindo o endpoint e retornando a resposta da entidade de acordo com a resposta do sistema (URI), seguindo
		// as boas práticas da arquitetura REST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		Cliente cliente = this.clienteService.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente = clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Retornando todas as clientes (endpoint: /clientes)
	// Usando DTO para retornar somente os dados realmente necessários (id e nome - sem os produtos)
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() { 
		List<Cliente> listaClientes = clienteService.findAll();
		// Convertendo a lista de Categorias para uma lista de CategoriasDTO
		List<ClienteDTO> listaClientesDTO = listaClientes.stream().map(objetoCliente -> new ClienteDTO(objetoCliente)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaClientesDTO);
	}
	
	/* Criando um método semelhante ao findAll, com a diferença de busca por página, e a criação de um novo
	   endpoint (clientes/page) e a passagem dos dados será por parâmetros opcionais. 
	   Exemplo: localhost/clientes/page?page=1&linesPerPage=20*/
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) { 
		Page<Cliente> listaClientes = clienteService.findPage(page, linesPerPage, orderBy, direction);
		// Convertendo a lista de Categorias para uma lista de CategoriasDTO
		Page<ClienteDTO> listaClientesDTO = listaClientes.map(objetoCliente -> new ClienteDTO(objetoCliente));
		return ResponseEntity.ok().body(listaClientesDTO);
	}
	
}
