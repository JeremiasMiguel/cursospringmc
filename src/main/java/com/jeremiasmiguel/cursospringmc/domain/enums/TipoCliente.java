package com.jeremiasmiguel.cursospringmc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa física"),
	PESSOAJURIDICA(2, "Pessoa jurídica");
	
	private int codigo;
	private String descricao;
	
	// Construtores de enumerações sempre são privados
	private TipoCliente(int codigo, String descricao) {
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
	public static TipoCliente toEnum(Integer codigo) {
		if(codigo == null) {
			return null;
		}
		
		for(TipoCliente tipoCliente : TipoCliente.values()) {
			if(codigo.equals(tipoCliente.getCodigo())) {
				return tipoCliente;
			}
		}
		
		throw new IllegalArgumentException("Identificador inválido: " + codigo);
	}
}
