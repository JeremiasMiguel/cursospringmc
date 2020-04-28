package com.jeremiasmiguel.cursospringmc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.jeremiasmiguel.cursospringmc.domain.enums.Perfil;
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
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public void instantiateTestDatabase() throws ParseException {
		// PRODUTO E CATEGORIA

		Categoria cat1 = new Categoria(null, "Informática");
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
		Produto p12 = new Produto(null, "Produto 12", 10.00);
		Produto p13 = new Produto(null, "Produto 13", 10.00);
		Produto p14 = new Produto(null, "Produto 14", 10.00);
		Produto p15 = new Produto(null, "Produto 15", 10.00);
		Produto p16 = new Produto(null, "Produto 16", 10.00);
		Produto p17 = new Produto(null, "Produto 17", 10.00);
		Produto p18 = new Produto(null, "Produto 18", 10.00);
		Produto p19 = new Produto(null, "Produto 19", 10.00);
		Produto p20 = new Produto(null, "Produto 20", 10.00);
		Produto p21 = new Produto(null, "Produto 21", 10.00);
		Produto p22 = new Produto(null, "Produto 22", 10.00);
		Produto p23 = new Produto(null, "Produto 23", 10.00);
		Produto p24 = new Produto(null, "Produto 24", 10.00);
		Produto p25 = new Produto(null, "Produto 25", 10.00);
		Produto p26 = new Produto(null, "Produto 26", 10.00);
		Produto p27 = new Produto(null, "Produto 27", 10.00);
		Produto p28 = new Produto(null, "Produto 28", 10.00);
		Produto p29 = new Produto(null, "Produto 29", 10.00);
		Produto p30 = new Produto(null, "Produto 30", 10.00);
		Produto p31 = new Produto(null, "Produto 31", 10.00);
		Produto p32 = new Produto(null, "Produto 32", 10.00);
		Produto p33 = new Produto(null, "Produto 33", 10.00);
		Produto p34 = new Produto(null, "Produto 34", 10.00);
		Produto p35 = new Produto(null, "Produto 35", 10.00);
		Produto p36 = new Produto(null, "Produto 36", 10.00);
		Produto p37 = new Produto(null, "Produto 37", 10.00);
		Produto p38 = new Produto(null, "Produto 38", 10.00);
		Produto p39 = new Produto(null, "Produto 39", 10.00);
		Produto p40 = new Produto(null, "Produto 40", 10.00);
		Produto p41 = new Produto(null, "Produto 41", 10.00);
		Produto p42 = new Produto(null, "Produto 42", 10.00);
		Produto p43 = new Produto(null, "Produto 43", 10.00);
		Produto p44 = new Produto(null, "Produto 44", 10.00);
		Produto p45 = new Produto(null, "Produto 45", 10.00);
		Produto p46 = new Produto(null, "Produto 46", 10.00);
		Produto p47 = new Produto(null, "Produto 47", 10.00);
		Produto p48 = new Produto(null, "Produto 48", 10.00);
		Produto p49 = new Produto(null, "Produto 49", 10.00);
		Produto p50 = new Produto(null, "Produto 50", 10.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
				p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
				p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));
		
		p12.getCategorias().add(cat1);
		p13.getCategorias().add(cat1);
		p14.getCategorias().add(cat1);
		p15.getCategorias().add(cat1);
		p16.getCategorias().add(cat1);
		p17.getCategorias().add(cat1);
		p18.getCategorias().add(cat1);
		p19.getCategorias().add(cat1);
		p20.getCategorias().add(cat1);
		p21.getCategorias().add(cat1);
		p22.getCategorias().add(cat1);
		p23.getCategorias().add(cat1);
		p24.getCategorias().add(cat1);
		p25.getCategorias().add(cat1);
		p26.getCategorias().add(cat1);
		p27.getCategorias().add(cat1);
		p28.getCategorias().add(cat1);
		p29.getCategorias().add(cat1);
		p30.getCategorias().add(cat1);
		p31.getCategorias().add(cat1);
		p32.getCategorias().add(cat1);
		p33.getCategorias().add(cat1);
		p34.getCategorias().add(cat1);
		p35.getCategorias().add(cat1);
		p36.getCategorias().add(cat1);
		p37.getCategorias().add(cat1);
		p38.getCategorias().add(cat1);
		p39.getCategorias().add(cat1);
		p40.getCategorias().add(cat1);
		p41.getCategorias().add(cat1);
		p42.getCategorias().add(cat1);
		p43.getCategorias().add(cat1);
		p44.getCategorias().add(cat1);
		p45.getCategorias().add(cat1);
		p46.getCategorias().add(cat1);
		p47.getCategorias().add(cat1);
		p48.getCategorias().add(cat1);
		p49.getCategorias().add(cat1);
		p50.getCategorias().add(cat1);

		// Adicionando produtos às categorias equivalentes
		// CATEGORIA 1 - Informática (Computador, impressora e mouse)
		cat1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		// CATEGORIA 2 - Escritório (Impressora e mesa de escritório)
		categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
		categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
		categoria5.getProdutos().addAll(Arrays.asList(produto8));
		categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
		categoria7.getProdutos().addAll(Arrays.asList(produto11));

		// Adicionando categorias aos produtos que se adequam a mesma
		// PRODUTO 1 - Computador (informática, eletrônicos)
		produto1.getCategorias().addAll(Arrays.asList(cat1, categoria4));
		// PRODUTO 2 - Impressora (informática, escritório, eletrônicos)
		produto2.getCategorias().addAll(Arrays.asList(cat1, categoria2, categoria4));
		// PRODUTO 3 - Mouse (informática e eletrônicos)
		produto3.getCategorias().addAll(Arrays.asList(cat1, categoria4));
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
				Arrays.asList(cat1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7,
				produto8, produto9, produto10, produto11));
		
		produtoRepository.saveAll(Arrays.asList(p12, p13, p14, p15, p16, p17, p18, p19, p20,
				p21, p22, p23, p24, p25, p26, p27, p28, p29, p30, p31, p32, p34, p35, p36, p37, p38,
				p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50));
		
		// ----------------------

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

		// Inserindo cliente e alguns telefones (senha criptografada)
		Cliente cliente1 = new Cliente(null, "Maria Silva", "emailtestespringcursomc@gmail.com", "36378912377", TipoCliente.PESSOAFISICA, passwordEncoder.encode("123"));
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		// Cliente ADMIN
		Cliente cliente2 = new Cliente(null, "Ana Costa", "olapis2013@gmail.com", "14101176779", TipoCliente.PESSOAFISICA, passwordEncoder.encode("123"));
		cliente2.addPerfil(Perfil.ADMIN);
		cliente2.getTelefones().addAll(Arrays.asList("32553333", "93838888"));

		// Inserindo endereços e relacionando a cidades e clientes
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1, cidade2);
		
		Endereco endereco3 = new Endereco(null, "Avenida Floriano", "2106", null, "Centro", "88780090", cliente2, cidade2);

		// Relacionando os endereços a um respectivo cliente (o cliente tem dois
		// endereços diferentes)
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		cliente2.getEnderecos().addAll(Arrays.asList(endereco3));

		// salvando no banco de dados, com o repository
		clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));

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
