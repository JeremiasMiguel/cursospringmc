package com.jeremiasmiguel.cursospringmc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.domain.Produto;
import com.jeremiasmiguel.cursospringmc.repositories.CategoriaRepository;
import com.jeremiasmiguel.cursospringmc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursospringmcApplication implements CommandLineRunner {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursospringmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// instanciando categorias
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		// instanciando produtos
		Produto produto1 = new Produto(null, "Computador", 2099.0);
		Produto produto2 = new Produto(null, "Impressora", 899.0);
		Produto produto3 = new Produto(null, "Mouse", 89.90);
		
		// adicionando produtos às categorias equivalentes
		// CATEGORIA 1 - Informática (Computador, impressora e mouse)
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		// CATEGORIA 2 - Escritório (Impressora)
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		// adicionando categorias aos produtos que se adequam a mesma
		// PRODUTO 1 - Computador (informática)
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		// PRODUTO 2 - Impressora (informática e escritório)
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		// PRODUTO 3 - Mouse (informática)
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		
		// salvando no banco de dados, com o repository
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
	}

}
