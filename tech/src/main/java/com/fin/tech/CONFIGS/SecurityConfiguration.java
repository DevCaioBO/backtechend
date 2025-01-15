package com.fin.tech.CONFIGS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	MySecurityFilter mySecurityFilter;
	
	
	
	  /*private final OAuth2SucessAuth oAuth2SucessAuth;

	    public SecurityConfiguration(OAuth2SucessAuth oAuth2SucessAuth) {
	        this.oAuth2SucessAuth = oAuth2SucessAuth;
	    }*/
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.cors()
				.and()
				.sessionManagement
				(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize ->authorize
						
						.requestMatchers(HttpMethod.POST, "/tech/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/tech/oauth2/login").permitAll()
						.requestMatchers(HttpMethod.POST, "/tech/login").permitAll()
						.requestMatchers(HttpMethod.POST, "tech/google").permitAll()
						.requestMatchers(HttpMethod.POST, "/tech/collect").authenticated()
					
						.requestMatchers(HttpMethod.POST, "/tech/updating").authenticated()
						.requestMatchers(HttpMethod.POST, "/account/create").authenticated()
						.requestMatchers(HttpMethod.POST, "/account/collect").authenticated()
						.requestMatchers(HttpMethod.GET, "/account/total").authenticated()
				.anyRequest().authenticated()
		)
		       
		         .logout(logout -> 
		         logout
		         .logoutUrl("/logout")  
		         .logoutSuccessUrl("http://localhost:5173/login")  
		     )
		         .logout(logout-> logout.logoutRequestMatcher(new AntPathRequestMatcher("http://localhost:5173/login")))
				.addFilterBefore(mySecurityFilter, UsernamePasswordAuthenticationFilter.class )
				.build();
				
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}

}