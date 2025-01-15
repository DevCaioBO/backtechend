package com.fin.tech.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fin.tech.models.UsersModel;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(UsersModel usersModel) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(usersModel.getLogin())
					.withClaim("userId", usersModel.getId())
					.withClaim("role", usersModel.getRole().name())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		}catch(JWTCreationException exception) {
			throw new RuntimeException("Erro enquanto gerava o token" + exception);
		}
	}
	public String validateToken(String token) {
		try {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm)
				.withIssuer("auth-api")
				.build()
				.verify(token)
				.getSubject();
		}catch(JWTVerificationException exception) {
			return "";
		}
	}
	//public Instant genExpirationDate() {
	//	return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	//}
	
	public Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
