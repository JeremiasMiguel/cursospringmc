package com.jeremiasmiguel.cursospringmc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	// do outro da associação já foram buscados os objetos, então é freada essa busca por categorias
	@JsonBackReference
	@ManyToMany
	@JoinTable(
		name="PRODUTO_CATEGORIA",
		joinColumns = @JoinColumn(name = "idProduto"),
		inverseJoinColumns = @JoinColumn(name = "idCategoria")
	)
	// acima: mapeamento da lista de categorias informando qual vai ser a tabela que vai intermediar
	// no banco de dados as tabelas PRODUTO e CATEGORIA, visto que é uma associação muitos para muitos,
	// indicando também as chaves estrangeiras que chegarão e o nome das mesmas
	private List<Categoria> categorias = new ArrayList<>(); 
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	// OneToMany -> O produto individual sabe quais itens se referem
	// mappedBy -> Do outro lado há a classe associativa ItemPedido que tem o objeto ID (ItemPedidoFK id),
	// que se trata de um objeto auxiliar que tem a referência para o produto
	// JsonIgnore -> Inibe que os produtos saibam quais itens o referem, é mais plausível que os
	// itens saibam os produtos aos quais estão relacionados
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	@JsonIgnore
	// JsonIgnore -> Para que não haja a serialização dos pedidos, e que ocorra uma serialização cíclica json
	public List<Pedido> getPedidos() {
		List<Pedido> lista = new ArrayList<>();
		// Para cada ItemPedido composto na lista de (itens), há a adição o pedido associado
		for(ItemPedido item : this.itens) {
			lista.add(item.getPedido());
		}
		return lista;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
