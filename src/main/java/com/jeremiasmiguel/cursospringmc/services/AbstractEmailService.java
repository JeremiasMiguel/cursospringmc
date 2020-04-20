package com.jeremiasmiguel.cursospringmc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jeremiasmiguel.cursospringmc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private JavaMailSender javaMailSender;
	
	// Buscando remetente no application.properties
	@Value("${default.sender}")
	private String sender = "jjjeremiasmiguel@gmail.com";
	
	// Método reaproveitado pelas classes que a estendem
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
	
	/* Método que será responsável por retornar o HTML preenchido com
	 * os dados de um pedido, a partir do template Thymeleaf
	*/
	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		// Enviando o objeto Pedido para o template thymeleaf, com o apelido "pedido"
		context.setVariable("pedido", pedido);
		// Buscando o caminho do HTML específico para a operação com o seu conteúdo
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	// Método reaproveitado pelas classes que a estendem
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mimeMessage = prepareMimeMessageFromPedido(pedido);
			// Utilizando padrão Template Method
			sendHtmlEmail(mimeMessage);
		} 
		catch(MessagingException e) {
			// Se der algum erro, há o envio somente por texto plano, sem ser com o html
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(pedido.getCliente().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(pedido), true);
		return mimeMessage;
	}
	
}
