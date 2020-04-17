package com.jeremiasmiguel.cursospringmc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.jeremiasmiguel.cursospringmc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// Buscando remetente no application.properties
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPedido(pedido);
		// Utilizando padrão Template Method
		sendEmail(simpleMailMessage);
	}
	
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(pedido.getCliente().getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("Pedido confirmado! Código: " + pedido.getId());
		// Garantindo que a data seja igual ao do servidor
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText(pedido.toString());
		return simpleMailMessage;
	}
	
}
