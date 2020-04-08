package com.jeremiasmiguel.cursospringmc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

// indicando que a classe vai ser uma entidade do JPA
@Entity
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// indicando o ID da entidade e gerando o valor com IDENTITY
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	// indica o lado que é desejável que venham os objetos associados (os produtos pertencentes
	// de categoria)
	// na classe produto então, há o travamento de busca por categorias, com outra anotação
	//@JsonManagedReference
	@ManyToMany(mappedBy = "categorias")
	// acima: é realizado o mapeamento do atributo, donde foi realizada a criação da tabela
	// intermediadora, na classe Produto, no atributo CATEGORIAS, com isso, não é necessário
	// refazer a tabela de meio de campo
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria() {
		
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
