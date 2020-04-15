package com.jeremiasmiguel.cursospringmc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	// Adicionando data de vencimento ao pagamento (7 dias)
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date instanteDoPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pagamentoComBoleto.setDataVencimento(calendar.getTime());
	}
	
}
