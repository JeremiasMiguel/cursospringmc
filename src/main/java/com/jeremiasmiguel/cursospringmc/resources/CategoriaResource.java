package com.jeremiasmiguel.cursospringmc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria categoria1 = new Categoria(1, "Informática");
		Categoria categoria2 = new Categoria(2, "Escritório");
		
		List<Categoria> listaCategorias = new ArrayList<>();
		listaCategorias.add(categoria1);
		listaCategorias.add(categoria2);
		
		return listaCategorias;
	}
	
}
