package com.jeremiasmiguel.cursospringmc.services;

import org.springframework.mail.SimpleMailMessage;

import com.jeremiasmiguel.cursospringmc.domain.Pedido;

public interface EmailService {

	public void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);
	
}
