package com.jeremiasmiguel.cursospringmc.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public String listar() {
		return "REST estáa funfando";
	}
	
}
