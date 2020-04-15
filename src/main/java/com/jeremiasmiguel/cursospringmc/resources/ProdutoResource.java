package com.jeremiasmiguel.cursospringmc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeremiasmiguel.cursospringmc.domain.Produto;
import com.jeremiasmiguel.cursospringmc.dto.ProdutoDTO;
import com.jeremiasmiguel.cursospringmc.resources.utils.URL;
import com.jeremiasmiguel.cursospringmc.services.ProdutoService;

// indicando que a classe é um controlador REST
@RestController
// que vai responder pelo endpoint abaixo
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private ProdutoService produtoService;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) { // indicando que o ID da URL vai ter que ir pro id variável
		Produto objetoProduto = produtoService.find(id);
		return ResponseEntity.ok().body(objetoProduto);
	}
	
	/* Criando um método semelhante ao findAll, com a diferença de busca por página,
	 * e a busca com inserção pelo parâmetro, com mais dois: o nome e as categorias relacionadas
	 * RequestParam -> Nome que vai ser utilizado na passagem de parãmetros
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		// Pegando o nome por parâmetro e transformando em uma lista de caracteres simples
		String nomeDecoded = URL.decodeParam(nome);
		// Pegando os números passados como parâmetro (sendo uma String), e transformando em uma lista de inteiros
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> listaProdutos = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		// Convertendo a lista de Categorias para uma lista de CategoriasDTO
		Page<ProdutoDTO> listaProdutosDTO = listaProdutos.map(objetoProduto -> new ProdutoDTO(objetoProduto));
		return ResponseEntity.ok().body(listaProdutosDTO);
	}
	
}
