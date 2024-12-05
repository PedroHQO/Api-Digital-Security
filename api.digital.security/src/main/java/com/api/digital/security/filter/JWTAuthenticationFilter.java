package com.api.digital.security.filter;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.digital.security.authenticate.JWTTokenProvider;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private final JWTTokenProvider jwtTokenProvider;
	private final UserDetailsService userDetailsService;

	public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Extaira o token JWT do cabeçalho
		String token = getJWTFromRequest(request);
		if (token != null) {
			LOGGER.debug("Token extraído do cabeçalho: {}", token);
		}

		// Valida o token e se o contexto de segurança ainda não possui autenticação
		if (token != null && jwtTokenProvider.validateToken(token)
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			LOGGER.debug("Token é válido");

			// Extrai o nome de usuário do token
			String username = jwtTokenProvider.getUsernameFromJWT(token);
			LOGGER.debug("Usuário extráido do token: {}", username);

			// carrega os detalhes do usuário
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (userDetails != null) {
				// Cria a autenticação com os detalhes do usuário
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Define a autenticação no contexto do Spring Security
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				LOGGER.debug("Autenticação definida para o usuário: {}", username);
			}
		} else {
			LOGGER.warn("Token inválido ou não encontrado");
		}

		// Continua a cadeia de filtros
		filterChain.doFilter(request, response);
	}

	// Extarai o token JWT do cabeçalho Authorization
	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Removerá o "BEarer " e retorna o token
		}
		return null;
	}

}
