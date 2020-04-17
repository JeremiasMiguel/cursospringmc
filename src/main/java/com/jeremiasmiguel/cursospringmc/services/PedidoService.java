package com.jeremiasmiguel.cursospringmc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.ItemPedido;
import com.jeremiasmiguel.cursospringmc.domain.PagamentoComBoleto;
import com.jeremiasmiguel.cursospringmc.domain.Pedido;
import com.jeremiasmiguel.cursospringmc.domain.enums.EstadoPagamento;
import com.jeremiasmiguel.cursospringmc.repositories.ItemPedidoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.PagamentoRepository;
import com.jeremiasmiguel.cursospringmc.repositories.PedidoRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	// indicando que o atributo/classe vai ser automaticamente instanciado
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	
	// Ao injetar essa interface, há a busca do Bean EmailService em config/TestConfig.java se for em modo TEST
	// Se for no modo DEV, instanciára o SMTPEmailService
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> objetoPedido = pedidoRepository.findById(id);
		return objetoPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	// Inserindo pedido e tudo o que nele é associado
	// Detalhe 1: algumas classes serão instanciadas na hora: como pagamento e itens
	// Detalhe 2: o id do Pagamento automaticamente fica igual ao do Pedido, segundo execução do próprio JPA
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, pedido.getInstante());
		}
		// Salvando pedido no BD
		pedido = pedidoRepository.save(pedido);
		// Salvando pagamento no BD
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		
		// Salvando os itens no BD
		itemPedidoRepository.saveAll(pedido.getItens());
		
		// Mandando um email depois de inserir o pedido
		emailService.sendOrderConfirmationEmail(pedido);
		
		return pedido;
	}
}
