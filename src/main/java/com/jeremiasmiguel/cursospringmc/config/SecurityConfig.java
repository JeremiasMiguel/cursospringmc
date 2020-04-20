package com.jeremiasmiguel.cursospringmc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	// Vetor de strings que definem quais os caminhos que estarão liberados
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };
	
	// Vetor de strings com caminhos somente de leitura, onde só é possivel recuperar os dados, sem manipulação dos mesmos
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 *  Buscando os profiles ativos do projeto, se estiver no profile TEST,
		 *  então é desejável o uso do h2-console, com isso, há uam configuração
		 *  que libera o acesso ao mesmo
		 */
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		/* 
		 * Verifica se tem um CORS ativo (o que de fato tem) e o utiliza,
		 * desativa o csrf (mat apoio 5)
		 */
		http.cors().and().csrf().disable();
		
		/*
		 * Configurando que todos os caminhos do vetor de strings estão permitidos em
		 * total, e os caminhos que não estiverem permitidos será necessária a autenticação.
		 * Ademais, nos caminhos somente de leitura, o único método HTTP que será possível é o GET
		 */
		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().anyRequest().authenticated();
		
		// Assegura que o backend não criará sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/*
	 * Definindo um Bean de CorsConfigurationSource que permite o acesso básico
	 * de múltiplas fontes, ou seja, permitindo o acesso aos endpoints por múltiplas
	 * fontes com as configurações básicas
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// Transforma a senha salva em um código hash gerado a partir dos caracteres da mesma
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
