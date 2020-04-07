package com.jeremiasmiguel.cursospringmc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.jeremiasmiguel.cursospringmc.domain.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// Inheritance -> Mapeamento de herança com o JPA, abordando a estratégia da criação de tabelas
// independentes para as classes que herdam a classe Pagamento (PagamentoComBoleto, PagamentoComCartao)
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// O ID do pagamento tem que ser o mesmo do pedido correspondente
	private Integer id;
	private Integer estadoPagamento;
	
	@OneToOne
	@JoinColumn(name = "idPedido")
	@MapsId
	// OneToOne -> Um pagamento pertence a só um pedido
	// JoinColumn -> Coluna correspondente ao ID do pedido
	// MapsId -> Garante que o ID do pagamento seja o mesmo que o ID do pedido, no banco de dados,
	// o identificador (chave primária) da entidade Pagamento será ID_PEDIDO
	private Pedido pedido;
	
	public Pagamento() {
		
	}

	public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
		super();
		this.id = id;
		this.estadoPagamento = estadoPagamento.getCodigo();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstadoPagamento() {
		return EstadoPagamento.toEnum(estadoPagamento);
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento.getCodigo();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
