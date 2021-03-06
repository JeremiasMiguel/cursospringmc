package com.jeremiasmiguel.cursospringmc.dto;

import java.io.Serializable;

import com.jeremiasmiguel.cursospringmc.domain.Cidade;

public class CidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public CidadeDTO() {
		
	}

	public CidadeDTO(Cidade estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
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
