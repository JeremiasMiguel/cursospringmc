package com.jeremiasmiguel.cursospringmc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jeremiasmiguel.cursospringmc.security.UserSpringSecurity;

// Classe que retorna o usuário logado
public class UserService {

	/*
	 * Função que verifica qual usuário está autenticado,
	 * retorna um UserSpringSecurity, que contém id, email, senha e os perfis de acesso
	 */
	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
