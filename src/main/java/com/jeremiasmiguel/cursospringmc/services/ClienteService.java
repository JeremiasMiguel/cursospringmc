package com.jeremiasmiguel.cursospringmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeremiasmiguel.cursospringmc.domain.Cidade;
import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.domain.Endereco;
import com.jeremiasmiguel.cursospringmc.domain.enums.Perfil;
import com.jeremiasmiguel.cursospringmc.domain.enums.TipoCliente;
import com.jeremiasmiguel.cursospringmc.dto.ClienteDTO;
import com.jeremiasmiguel.cursospringmc.dto.ClienteNewDTO;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.repositories.EnderecoRepository;
import com.jeremiasmiguel.cursospringmc.security.UserSpringSecurity;
import com.jeremiasmiguel.cursospringmc.services.exceptions.AuthorizationException;
import com.jeremiasmiguel.cursospringmc.services.exceptions.DataIntegrityException;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	// Para criptografar a senha no método fromDTO, na classe ClienteNewDTO
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Cliente find(Integer id) {
		
		UserSpringSecurity user = UserService.authenticated();
		// Verifica se o usuário tem permissão de perfil e que o id é igual ao que está autenticado
		if(user != null && !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado! ");
		}
		
		Optional<Cliente> objetoCliente = clienteRepository.findById(id);
		return objetoCliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	// Indicando que as entidades Endereco e Cliente são salvas em uma mesma transação
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	// O método SAVE serve tanto para inserir como para atualizar, a única diferença é o ID,
	// se for nulo, ele adiciona, se não for, ele atualiza
	public Cliente update(Cliente cliente) {
		// Verificando se o ID pesquisado para alteração existe ou não é nulo
		Cliente novoCliente = this.find(cliente.getId());
		// Atualizando o novoCliente com os dados que podem ser atualizados (nome e email)
		updateData(novoCliente, cliente);
		return clienteRepository.save(novoCliente);
	}

	public void delete(Integer id) {
		this.find(id);
		try {
			clienteRepository.deleteById(id);
		}
		/*
		 * Exceção causada pela tentativa de exclusão de uma entidade que contém outras
		 * relacionadas, por exemplo, a entidade Cliente pode conter entidades
		 * relacionados a ele, se houver, a exclusão é abortada por meio dessa captura
		 * de uma exceção personalizada criada (DataIntegrityException)
		 */
		catch (DataIntegrityViolationException exception) {
			throw new DataIntegrityException(
					"Não é possível realizar a exclusão de um Cliente que contenha Pedidos relacionados.");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	/*
	 * Função que define uma paginação de dados, onde é possível definir a página
	 * que se deseja verificar, quantas linhas serão mostradas por página, o
	 * atributo pelo qual se ordenará e a ordenação dos dados (asc ou desc)
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		// Busca dos dados no BD seguindo os preceitos do PageRequest, retornando uma
		// Page
		return clienteRepository.findAll(pageRequest);
	}

	// Convertendo uma Cliente em ClienteDTO
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		// Instanciando cliente já com a senha criptografada
		Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()), passwordEncoder.encode(clienteNewDTO.getSenha()));
		Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2()!=null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if (clienteNewDTO.getTelefone3()!=null) {
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cliente;
	}

	private void updateData(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
}
