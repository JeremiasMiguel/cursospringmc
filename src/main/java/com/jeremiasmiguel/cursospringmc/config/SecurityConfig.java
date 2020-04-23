package com.jeremiasmiguel.cursospringmc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jeremiasmiguel.cursospringmc.security.JWTAuthenticationFilter;
import com.jeremiasmiguel.cursospringmc.security.JWTAuthorizationFilter;
import com.jeremiasmiguel.cursospringmc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// EnableGlobalMethodSecurity -> Permite que seja possível posteriormente colocar anotações de pré-autorização nos endpoints
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	@Autowired
	/*
	 * O Spring Security busca uma classe que implementa essa interface,
	 * tal classe é a UserDetailsServiceImpl no pacote de services, após
	 * buscar, irá injetar tal serviço que diz quem é o responsável por
	 * buscar um usuário por email
	 */
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	// Vetor de strings que definem quais os caminhos que estarão liberados
	private static final String[] PUBLIC_MATCHERS = { 
			"/h2-console/**" 
	};
	
	// Vetor de strings com caminhos somente de leitura, onde só é possivel recuperar os dados, sem manipulação dos mesmos
	private static final String[] PUBLIC_MATCHERS_GET = { 
			"/produtos/**", 
			"/categorias/**" 
	};
	
	// Endpoints onde só são permitidas as operações de POST
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clientes",
			"/clientes/picture",
			"/auth/forgot/**"
	};

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
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.anyRequest().authenticated();
		
		// Adicionando filtro de autenticação
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		// Adicionando filtro de autorização
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		// Assegura que o backend não criará sessão de usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/* 
	 * Método configure com sobrecarga de método, onde se distingue do primeiro pelo parâmetro,
	 * nesse método, define-se quem é o UserDetailsService e o algoritmo de criptografia da senha
	 */
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
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
