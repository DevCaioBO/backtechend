package com.fin.tech.CONFIGS;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fin.tech.repositorys.UsersRepository;
import com.fin.tech.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class MySecurityFilter extends OncePerRequestFilter {
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UsersRepository repo;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	    String path = request.getRequestURI();


	    if (path.equals("/tech/register") || path.equals("/tech/login") || path.equals("tech/google") || path.equals("/oauth2/authorization/google") || path.equals("/tech/oauth2/login")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
		
		
		var token = this.recoverToken(request);
		
		if(token != null) {
			var login = tokenService.validateToken(token);
			UserDetails user = repo.findByLogin(login);
			
			var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);		
		}
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		
		if(authHeader == null) return null;
		return authHeader.replace("Bearer ","");
	}
}
