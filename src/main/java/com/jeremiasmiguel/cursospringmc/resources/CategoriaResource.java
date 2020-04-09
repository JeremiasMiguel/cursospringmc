package com.jeremiasmiguel.cursospringmc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.dto.CategoriaDTO;
import com.jeremiasmiguel.cursospringmc.services.CategoriaService;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		Categoria objetoCategoria = categoriaService.find(id);
		return ResponseEntity.ok().body(objetoCategoria);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria categoria) { // Faz que o json se converta em objeto Java
		categoria = categoriaService.insert(categoria);
		// Definindo o endpoint e retornando a resposta da entidade de acordo com a resposta do sistema (URI), seguindo
		// as boas práticas da arquitetura REST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id) {
		categoria.setId(id);
		categoria = categoriaService.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Categoria> delete(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	// Retornando todas as categorias (endpoint: /categorias)
	// Usando DTO para retornar somente os dados realmente necessários (id e nome - sem os produtos)
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() { 
		List<Categoria> listaCategorias = categoriaService.findAll();
		// Convertendo a lista de Categorias para uma lista de CategoriasDTO
		List<CategoriaDTO> listaCategoriasDTO = listaCategorias.stream().map(objetoCategoria -> new CategoriaDTO(objetoCategoria)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCategoriasDTO);
	}
	
	/* Criando um método semelhante ao findAll, com a diferença de busca por página, e a criação de um novo
	   endpoint (categorias/page) e a passagem dos dados será por parâmetros opcionais. 
	   Exemplo: localhost/categorias/page?page=1&linesPerPage=20*/
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) { 
		Page<Categoria> listaCategorias = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		// Convertendo a lista de Categorias para uma lista de CategoriasDTO
		Page<CategoriaDTO> listaCategoriasDTO = listaCategorias.map(objetoCategoria -> new CategoriaDTO(objetoCategoria));
		return ResponseEntity.ok().body(listaCategoriasDTO);
	}
}
