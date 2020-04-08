package com.jeremiasmiguel.cursospringmc.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	// indicando o ID da entidade e gerando o valor com IDENTITY
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instante;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
	//@JsonManagedReference
	// OneToOne -> Um pedido só tem um pagamento
	// (cascade = CascadeType.ALL) -> Inibe o erro de entidade transiente quando o pedido e o pagamento forem salvos
	// mappedBy -> Informa que já houve mapeamento por meio do atributo Pedido, na outra classe que se associa (Pagamento)
	private Pagamento pagamento;

	@ManyToOne
	@JoinColumn(name = "idCliente") 
	//@JsonManagedReference
	// JsonManagedReference -> Permite que os pedidos serializem os clientes, por outro lado,
	// inibe que os clientes serializem os pedidos (Anotação JsonBackReference na classe Cliente - atributo de Pedido)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "idEnderecoEntrega")
	// JoinColumn -> O nome da chave estrangeira na tabela Pedido
	private Endereco enderecoDeEntrega;
	
	@OneToMany(mappedBy = "id.pedido")
	// OneToMany -> O pedido individual sabe/contém uma lista de itens
	// mappedBy -> Do outro lado há a classe associativa ItemPedido que tem o objeto ID (ItemPedidoFK id),
	// que se trata de um objeto auxiliar que tem a referência para o pedido
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {

	}

	// O pagamento não foi inserido no contrutor porque é interdependente e é necessário que um
	// seja prioritário na criação
	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
