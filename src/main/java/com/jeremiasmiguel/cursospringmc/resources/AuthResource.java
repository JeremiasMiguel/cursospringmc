package com.jeremiasmiguel.cursospringmc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeremiasmiguel.cursospringmc.dto.EmailDTO;
import com.jeremiasmiguel.cursospringmc.security.JWTUtil;
import com.jeremiasmiguel.cursospringmc.security.UserSpringSecurity;
import com.jeremiasmiguel.cursospringmc.services.AuthService;
import com.jeremiasmiguel.cursospringmc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;
	
	/*
	 * Método que gera outro token de autenticação quando um
	 * antigo estiver perto de sua expiração, e mantém o
	 * usuário conectado. Logicamente o usuário deve estar logado
	 * para acessar esse endpoint
	 */
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSpringSecurity user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		
		authService.sendNewPassword(emailDTO.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}
