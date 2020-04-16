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
	
	public static void main(String[] args) {
		SpringApplication.run(CursospringmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
			
	}

}
