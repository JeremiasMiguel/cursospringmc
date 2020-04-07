package com.jeremiasmiguel.cursospringmc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"), 
	QUITADO(2, "Quitado"), 
	CANCELADO(3, "Cancelado");

	private int codigo;
	private String descricao;

	// Construtores de enumerações sempre são privados
	private EstadoPagamento(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return this.codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	// Recebimento de um código e retorno de uma instância ENUM
	// Método static: pode ocorrer mesmo que não tenha um objeto instanciado, sem depender
	// do conteúdo de um objeto e/ou a instância de uma classe, pode chamar qualquer método
	// da classe ou manipular algum campo da mesma
	public static EstadoPagamento toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (EstadoPagamento estadoPagamento : EstadoPagamento.values()) {
			if (codigo.equals(estadoPagamento.getCodigo())) {
				return estadoPagamento;
			}
		}

		throw new IllegalArgumentException("Identificador inválido: " + codigo);
	}
}
