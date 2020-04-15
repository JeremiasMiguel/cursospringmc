package com.jeremiasmiguel.cursospringmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.domain.Produto;
import com.jeremiasmiguel.cursospringmc.repositories.CategoriaRepository;
import com.jeremiasmiguel.cursospringmc.repositories.ProdutoRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> objetoProduto = produtoRepository.findById(id);
		return objetoProduto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	/*
	 * Parâmetros: 
	 * - nome: um trecho de nome de produto 
	 * - idsCategorias: uma lista de códigos de categorias 
	 *  
	 * Retorno: 
	 * A listagem de produtos que contém o trecho de nome dado e
	 * que pertencem a pelo menos uma das categorias dadas,
	 * isso realizado diretamente com o repository, manipulando o BD
	 */
	public Page<Produto> search(String nome, List<Integer> idsCategorias, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		// Buscando categorias por meio da lista de IDS 
		List<Categoria> categorias = categoriaRepository.findAllById(idsCategorias);
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
