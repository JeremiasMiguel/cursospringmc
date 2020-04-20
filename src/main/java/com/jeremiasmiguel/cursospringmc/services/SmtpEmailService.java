package com.jeremiasmiguel.cursospringmc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	// Mostra o email do log no servidor
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOG.info("Enviando email...");
		mailSender.send(simpleMailMessage);
		LOG.info("Email enviado!");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOG.info("Enviando email HTML...");
		javaMailSender.send(mimeMessage);
		LOG.info("Email enviado!");
	}

}
