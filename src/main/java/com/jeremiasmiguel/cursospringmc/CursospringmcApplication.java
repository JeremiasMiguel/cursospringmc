package com.jeremiasmiguel.cursospringmc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jeremiasmiguel.cursospringmc.domain.Categoria;
import com.jeremiasmiguel.cursospringmc.domain.Cidade;
import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.domain.Endereco;
import com.jeremiasmiguel.cursospringmc.domain.Estado;
import com.jeremiasmiguel.cursospringmc.domain.ItemPedido;
import com.jeremiasmiguel.cursospringmc.domain.Pagamento;
import com.jeremiasmiguel.cursospringmc.domain.PagamentoComBoleto;
import com.jeremiasmiguel.cursospringmc.domain.PagamentoComCartao;
import com.jeremiasmiguel.cursospringmc.domain.Pedido;
import com.jeremiasmiguel.cursospringmc.domain.Produto;
import com.jeremiasmiguel.cursospringmc.domain.enums.EstadoPagamento;
import com.jeremiasmiguel.cursospringmc.domain.enums.TipoCliente;
import com.jeremiasmiguel.cursospringmc.repositories.CategoriaRepository;
import com.jeremiasmiguel.cursospringmc.repositories.CidadeRepository;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.repositories.EnderecoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.EstadoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.ItemPedidoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.PagamentoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.PedidoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursospringmcApplication implements CommandLineRunner {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	@Autowired
	PagamentoRepository pagamentoRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursospringmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// PRODUTO E CATEGORIA
		
		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		
		Produto produto1 = new Produto(null, "Computador", 2099.0);
		Produto produto2 = new Produto(null, "Impressora", 899.0);
		Produto produto3 = new Produto(null, "Mouse", 89.90);
		
		// Adicionando produtos às categorias equivalentes
		// CATEGORIA 1 - Informática (Computador, impressora e mouse)
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		// CATEGORIA 2 - Escritório (Impressora)
		categoria2.getProdutos().addAll(Arrays.asList(produto2));
		
		// Adicionando categorias aos produtos que se adequam a mesma
		// PRODUTO 1 - Computador (informática)
		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		// PRODUTO 2 - Impressora (informática e escritório)
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		// PRODUTO 3 - Mouse (informática)
		produto3.getCategorias().addAll(Arrays.asList(categoria1));
		
		// salvando no banco de dados, com o repository
		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));
		
		// ESTADO E CIDADE
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		// Muitos para um -> Associação direta no construtor
		Cidade cidade1 = new Cidade(null, "Uberlândia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado2);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		// salvando no banco de dados, com o repository
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		// CLIENTE, ENDEREÇO E TELEFONES
		
		// Inserindo cliente e alguns telefones
		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		// Inserindo endereços e relacionando a cidades e clientes
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
	
		// Relacionando os endereços a um respectivo cliente (o cliente tem dois endereços diferentes)
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		// salvando no banco de dados, com o repository
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
		
		// PEDIDO, ENDEREÇO DE ENTREGA, PAGAMENTO E ESTADO_PAGAMENTO
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		// Inserindo pedidos e relacionando endereço e cliente
		Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		// Inserindo pagamentos e relacionando a um pedido, e logo após confirmando a forma de 
		// pagamento dos pedidos
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);
		
		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2, simpleDateFormat.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);
		
		// Adicionando os pedidos que foram criados a um respectivo cliente que pediu
		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		// Salvando no banco de dados (repository)
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));
		
		// ITEMPEDIDO (Classe associativa entre Pedido e Produto)
		
		// Inserindo os itens
		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, produto1.getPreco());
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, produto3.getPreco());
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, produto2.getPreco());
		
		// Interligando os itens respectivos de cada Pedido
		pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItens().addAll(Arrays.asList(itemPedido3));
		
		// Interligando os itens respectivos de cada Produto
		produto1.getItens().addAll(Arrays.asList(itemPedido1));
		produto2.getItens().addAll(Arrays.asList(itemPedido3));
		produto3.getItens().addAll(Arrays.asList(itemPedido2));
		
		// Salvando os itens no BD
		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
	}

}
