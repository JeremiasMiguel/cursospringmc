package com.jeremiasmiguel.cursospringmc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class DBService {

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

	public void instantiateTestDatabase() throws ParseException {
		// PRODUTO E CATEGORIA

		Categoria categoria1 = new Categoria(null, "Informática");
		Categoria categoria2 = new Categoria(null, "Escritório");
		Categoria categoria3 = new Categoria(null, "Casa, mesa e banho");
		Categoria categoria4 = new Categoria(null, "Eletrônicos");
		Categoria categoria5 = new Categoria(null, "Jardinagem");
		Categoria categoria6 = new Categoria(null, "Decoração");
		Categoria categoria7 = new Categoria(null, "Perfumaria");

		Produto produto1 = new Produto(null, "Computador", 2099.0);
		Produto produto2 = new Produto(null, "Impressora", 899.0);
		Produto produto3 = new Produto(null, "Mouse", 89.90);
		Produto produto4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto produto5 = new Produto(null, "Toalha", 50.00);
		Produto produto6 = new Produto(null, "Colcha", 200.00);
		Produto produto7 = new Produto(null, "TV true color", 1200.00);
		Produto produto8 = new Produto(null, "Roçadeira", 800.00);
		Produto produto9 = new Produto(null, "Abajour", 100.00);
		Produto produto10 = new Produto(null, "Pendente", 100.00);
		Produto produto11 = new Produto(null, "Shampoo", 90.00);

		// Adicionando produtos às categorias equivalentes
		// CATEGORIA 1 - Informática (Computador, impressora e mouse)
		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		// CATEGORIA 2 - Escritório (Impressora e mesa de escritório)
		categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
		categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
		categoria5.getProdutos().addAll(Arrays.asList(produto8));
		categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
		categoria7.getProdutos().addAll(Arrays.asList(produto11));

		// Adicionando categorias aos produtos que se adequam a mesma
		// PRODUTO 1 - Computador (informática, eletrônicos)
		produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		// PRODUTO 2 - Impressora (informática, escritório, eletrônicos)
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
		// PRODUTO 3 - Mouse (informática e eletrônicos)
		produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		produto4.getCategorias().addAll(Arrays.asList(categoria2));
		produto5.getCategorias().addAll(Arrays.asList(categoria3));
		produto6.getCategorias().addAll(Arrays.asList(categoria3));
		produto7.getCategorias().addAll(Arrays.asList(categoria4));
		produto8.getCategorias().addAll(Arrays.asList(categoria5));
		produto9.getCategorias().addAll(Arrays.asList(categoria6));
		produto10.getCategorias().addAll(Arrays.asList(categoria6));
		produto11.getCategorias().addAll(Arrays.asList(categoria7));

		// salvando no banco de dados, com o repository
		categoriaRepository.saveAll(
				Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7,
				produto8, produto9, produto10, produto11));

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
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1,
				cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1,
				cidade2);

		// Relacionando os endereços a um respectivo cliente (o cliente tem dois
		// endereços diferentes)
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

		// salvando no banco de dados, com o repository
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

		// PEDIDO, ENDEREÇO DE ENTREGA, PAGAMENTO E ESTADO_PAGAMENTO

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// Inserindo pedidos e relacionando endereço e cliente
		Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("10/10/2017 19:35"), cliente1, endereco2);

		// Inserindo pagamentos e relacionando a um pedido, e logo após confirmando a
		// forma de
		// pagamento dos pedidos
		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
		pedido1.setPagamento(pagamento1);

		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				simpleDateFormat.parse("20/10/2017 00:00"), null);
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
