package com.jeremiasmiguel.cursospringmc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.domain.Produto;


// indicando que a interface é um Repository, e extendendo a classe JpaRepository indicando que a interface
// manuseará a classe Produto, com o identificador sendo um inteiro (atributo ID de produto)
// com ele, é possível realizar operações de acesso a dados
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	/*
	 * Função que busca um produto no banco de dados comparando o nome que foi passado como parâmetro,
	 * aliado às categorias que foram passadas em forma de lista, realizando uma filtragem do retorno
	 * dos nomes dos produtos que iriam retornar, retornando apenas os produtos com as categorias
	 * que foram realmente selecionadas, excluindo os outros que não contém alguma categoria que foi passada.
	 * Por fim, a realização de uma query, utilizando JPQL, que faz a seguinte funcionalidade:
	 * Seleciona de maneira distinta um objeto que faz parte da classe Produto, realizando uma interligação
	 * com INNER JOIN da lista de categorias presentes no Produto. Mas, faz isso logicamente se a String nome
	 * que foi passada estiver presente no nome do Produto e se a lista das categorias do Produto for a mesma
	 * que foi passada como parâmetro.
	 * 
	 * Anotação @Param:
	 * Informa a query que foi realizada que os parâmetros da função SEARCH farão parte da consulta realizada
	 */
	
	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
}
