package com.jeremiasmiguel.cursospringmc.domain.enums;

public enum Perfil {

	// Utiliza-se a notação (ROLE_) porque é uma exigência do Spring Security
	ADMIN(1, "ROLE_ADMIN"), 
	CLIENTE(2, "ROLE_CLIENTE");

	private int codigo;
	private String descricao;

	// Construtores de enumerações sempre são privados
	private Perfil(int codigo, String descricao) {
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
	public static Perfil toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (Perfil perfil : Perfil.values()) {
			if (codigo.equals(perfil.getCodigo())) {
				return perfil;
			}
		}

		throw new IllegalArgumentException("Identificador inválido: " + codigo);
	}
}
