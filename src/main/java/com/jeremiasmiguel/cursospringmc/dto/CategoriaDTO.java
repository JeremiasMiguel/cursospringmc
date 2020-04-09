package com.jeremiasmiguel.cursospringmc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;

/* Classe que tem como objetivo para definir os dados que deseja-se trafegar
quando forem necessárias a realização de operações básicas de Categoria.
Exemplo: quando for necessária a busca de todas as categorias, é desejável
com que não contenha os produtos relacionados, somente os atributos básicos
da mesma: id e nome */
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres.")
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
