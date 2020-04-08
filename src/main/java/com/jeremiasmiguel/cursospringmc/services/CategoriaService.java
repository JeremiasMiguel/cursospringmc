package com.jeremiasmiguel.cursospringmc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.repositories.CategoriaRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria find(Integer id) {
		Optional<Categoria> objetoCategoria = categoriaRepository.findById(id);
		return objetoCategoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())
		);
	} 
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}
	
	// O método SAVE serve tanto para inserir como para atualizar, a única diferença é o ID,
	// se for nulo, ele adiciona, se não for, ele atualiza
	public Categoria update(Categoria categoria) {
		// Verificando se o ID pesquisado para alteração existe ou não é nulo
		this.find(categoria.getId());
		return categoriaRepository.save(categoria);
	}
}
