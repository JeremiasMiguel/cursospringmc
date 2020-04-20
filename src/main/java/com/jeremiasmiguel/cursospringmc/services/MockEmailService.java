package com.jeremiasmiguel.cursospringmc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	// Mostra o email do log no servidor
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
			
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOG.info("Simulando envio de email");
		LOG.info(simpleMailMessage.toString());
		LOG.info("Email enviado");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOG.info("Simulando envio de email HTML...");
		LOG.info(mimeMessage.toString());
		LOG.info("Email enviado!");
	}

}
