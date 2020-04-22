package com.jeremiasmiguel.cursospringmc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.domain.Pedido;

public interface EmailService {

	public void sendOrderConfirmationEmail(Pedido pedido);
	
	public void sendEmail(SimpleMailMessage simpleMailMessage);
	
	public void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	public void sendHtmlEmail(MimeMessage mimeMessage);
	
	public void sendNewPasswordEmail(Cliente cliente, String newPass);
	
}
