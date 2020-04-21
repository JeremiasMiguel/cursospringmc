package com.jeremiasmiguel.cursospringmc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeremiasmiguel.cursospringmc.dto.CredenciaisDTO;

/*
 * Classe que intercepta as credenciais de acesso e verifica
 * se o usuário e a senha estão corretos.
 * Com a extensão da classe UsernamePasswordAuthenticationFilter,
 * o Spring Security sabe automaticamente que o filtro vai ter
 * que interceptar a requisição de login no endpoint /login,
 * próprio do Spring Security
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	// Método que tenta realizar a autenticação
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		
		try {
			/*
			 *  Operação que tenta pegar no objeto os dados de requisição (email e senha),
			 *  e depois converte para a classe CredenciaisDTO, com isso, é instanciado
			 *  um objeto CredenciaisDTO a partir dos dados fornecidos pela requisição
			 */
			CredenciaisDTO credenciaisDTO = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
		
			// Geração de um token pelo Spring Security (não é o do jpa)
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciaisDTO.getEmail(), credenciaisDTO.getSenha(), new ArrayList<>());
		
			/* 
			 * Instanciação de um objeto de autenticação que recebe um método que verifica
			 * se o usuário e senha realmente são válidos, com base no que foi definido nas
			 * classes UserDetails e UserDetailsService, usando os contratos já criados
			 */
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	/*
	 *  Se a autenticação acontecer, o que deve ser feito,
	 *  é gerado um token, que é adicionado na resposta da requisição.
	 *  O objeto auth recebido é o mesmo executado no método acima,
	 *  então a partir dele, esse método realiza suas operações
	 */
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		// Pega o email da pessoa que faz login
		String username = ((UserSpringSecurity) auth.getPrincipal()).getUsername();
		// Com o email buscado, é gerado um token com a passagem do email
		String token = jwtUtil.generateToken(username);
		// O token retornado acima é acrescentado no cabeçalho da requisição
		res.addHeader("Authorization", "Bearer " + token);
		
	}

}
