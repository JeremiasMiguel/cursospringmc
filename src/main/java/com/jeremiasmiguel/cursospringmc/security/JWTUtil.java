package com.jeremiasmiguel.cursospringmc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	// Gerando token JWT
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS512, this.secret.getBytes())
				.compact();
	}	
	
	public boolean tokenValido(String token) {
		/*
		 *  Os Claims são as reinvidicações de uma pessoa que deseja acessar um endpoint,
		 *  tais reinvidicações são o usuário e o tempo de expiração, que estão armazenadas
		 *  nos claims
		 */
		Claims claims = getClaims(token);
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			// Verifica se o usuário não é nulo e se o tempo não foi expirado
			if(username != null && expirationDate !=null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	// Função que recupera os Claims (reinvidicações) a partir de um token
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	// Busca um usuário a partir do token
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
}
