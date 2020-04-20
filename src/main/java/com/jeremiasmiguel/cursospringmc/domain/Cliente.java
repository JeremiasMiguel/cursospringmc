package com.jeremiasmiguel.cursospringmc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeremiasmiguel.cursospringmc.domain.enums.Perfil;
import com.jeremiasmiguel.cursospringmc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(unique=true)
	// O email deve ser único
	private String email;
	private String cpfOuCnpj;
	// Localmente, o tipo do cliente será tratado como inteiro. Externamente, será um atributo
	// TipoCliente normal. Para converter, há a busca do código no construtor e tratamento
	// correto com os getters e setters modificados para essa modificação
	private Integer tipoCliente;
	
	@JsonIgnore
	// JsonIgnore -> Para que não apareça o BCrypt da senha no corpo json
	private String senha;
	
	//@JsonManagedReference
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	// OneToMany -> Um para muitos. Um cliente pode ter vários endereços
	// mappedBy -> Indicando que já houve mapeamento na classe Endereco, com o atributo CLIENTE
	// CascadeType -> Como o endereço vai se comportar quando houver a exclusão de um cliente
	// ALL -> Quando houver manipulação no cliente, o endereço em toda parte será modificado também
	private List<Endereco> enderecos = new ArrayList<>();
	
	// Entidade fraca TELEFONE -> Pode se classificar como uma coleção de Strings relacionadas
	// ao cliente, por meio do SET, um conjunto que não aceita repetições
	@ElementCollection
	@CollectionTable(name = "TELEFONE")
	// No banco de dados, a tabela TELEFONE será criada com o idCliente e o atributo "telefones"
	// CollectionTable -> Indicando o nome da tabela auxiliar que vai conter os telefones no BD
	private Set<String> telefones = new HashSet<>();
	
	/*
	 *  Semelhante a classe Telefone
	 *  FetchType -> Faz com que ao buscar um cliente seja buscado seu tipo de usuário,
	 *  ou seja, serão buscados juntos no BD
	 *  CollectionTable -> Indicando o nome da tabela auxiliar que vai conter os perfis no BD
	 *  Será uma lista de inteiros, para depois o identificador ser convertido para Enum
	 */
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	@OneToMany(mappedBy = "cliente")
	//@JsonBackReference
	@JsonIgnore
	private List<Pedido> pedidos = new ArrayList<>();
	
	/* Todo cliente será tratado inicialmente com a enumeração Cliente
	 * logicamente. Posteriormente, poderão ser adicionados como Admin,
	 * de acordo com a escolha do administrador principal
	 */
	public Cliente() {
		addPerfil(Perfil.CLIENTE);
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipoCliente = (tipoCliente == null) ? null : tipoCliente.getCodigo();
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipoCliente() {
		return TipoCliente.toEnum(this.tipoCliente);
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente.getCodigo();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/*
	 * Retorna os perfis correspondentes ao Cliente que está apontado
	 * Iteração da lista retornando o perfil de acordo com o código e o cast para Enum,
	 * e após, transformando em uma lista set
	*/
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	/* Adiciona um perfil que for passado a um cliente específico,
	 * e transformando em código inteiro
	 */
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCodigo());
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
