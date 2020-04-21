package com.jeremiasmiguel.cursospringmc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jeremiasmiguel.cursospringmc.domain.Cliente;
import com.jeremiasmiguel.cursospringmc.repositories.ClienteRepository;
import com.jeremiasmiguel.cursospringmc.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	// Método que recebe um usuário (email) e retorna um UserDetails
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			// Se não houver o cliente pedido, lança uma exceção do próprio Spring Security
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
	}

}
