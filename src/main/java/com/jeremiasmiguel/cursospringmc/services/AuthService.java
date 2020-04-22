package com.jeremiasmiguel.cursospringmc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		// Busca o cliente pelo email passado por parâmetro
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado!");
		}
		
		String newPassword = newPassword();
		cliente.setSenha(passwordEncoder.encode(newPassword));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
		
	}

	// Gerando senha aleatória com 10 caracteres
	private String newPassword() {
		char[] vetor = new char[10];
		
		for(int i=0; i<10; i++) {
			vetor[i] = randomChar();
		}
		
		return new String(vetor);
	}

	private char randomChar() {
		
		// Escolher entre dígito, letra maiúscula e/ou minúscula
		int option = rand.nextInt(3);
		
		// Gera um dígito
		if(option == 0) {
			// Código decimal do zero: 48
			return (char) (rand.nextInt(10) + 48);
		}
		// Gera letra maiúscula
		else if(option == 1) {
			// Código decimal do A: 65
			return (char) (rand.nextInt(26) + 65);
		}
		// Gera letra minúscula
		else {
			// Código decimal do a: 97
			return (char) (rand.nextInt(26) + 97);
		}
	}
	
}
