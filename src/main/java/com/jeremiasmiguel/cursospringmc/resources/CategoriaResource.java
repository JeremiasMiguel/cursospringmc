package com.jeremiasmiguel.cursospringmc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
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
}
