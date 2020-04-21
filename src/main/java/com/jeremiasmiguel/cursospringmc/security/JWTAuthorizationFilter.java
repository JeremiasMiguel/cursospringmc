package com.jeremiasmiguel.cursospringmc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/*
 *  Classe que analisa o token de autorização para ver se o mesmo é válido,
 *  e pra isso é necessário extrair o usuário e buscar no BD de usuário e 
 *  verificar se o mesmo existe, buscando um usuário de acordo com o email pelo UserDetailsService
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// Intercepta a requisição e verifica se o usuário está autorizado
	// Método padrão que executa algo antes da requisição continuar
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// Pega o valor do cabeçalho (headers) na requisição - Authorization (Bearer ~tokenCaracteres~)
		String header = request.getHeader("Authorization");
		
		// Verifica se o token é válido (se não é nulo, e se começa com Bearer espaço)
		if(header != null && header.startsWith("Bearer ")) {
			/*
			 *  Criando um auth e atribuindo um método, esse método recebe o valor
			 *  seguinte a palavra "Bearer" (o token em si) e ele retorna um objeto
			 *  UsernamePasswordAuthenticationToken, se o token for válido,
			 *  senão retorna nulo.
			 *  Libera o acesso do usuário que deseja acessar o endpoint
			 */
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			// Se o auth não for nulo, ele é válido, e com isso, há a liberação de acesso
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			
			// Informa que o filtro pode seguir a execução normal da requisição
			chain.doFilter(request, response);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		// Se o token for válido, o usuário está autorizado
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			// Ao buscar o usuário, ele é retornado e há a verificação se o mesmo tem permissão
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}

}
