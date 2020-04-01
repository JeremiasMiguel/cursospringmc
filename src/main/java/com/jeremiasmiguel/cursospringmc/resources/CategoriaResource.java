package com.jeremiasmiguel.cursospringmc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> find(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		Categoria objetoCategoria = categoriaService.buscar(id);
		return ResponseEntity.ok().body(objetoCategoria);
	}
	
}
