package com.abc.bankingApp.properties;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.abc.bankingApp.common.JwtTokenMalformedException;
import com.abc.bankingApp.common.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expireTime}")
	private long tokenValidity;

	public String generateToken(Authentication authentication) {
		User user=((User)authentication.getPrincipal());
		System.out.println("token generate with these "+user.getUsername());
		Claims subject = Jwts.claims().setSubject(user.getUsername());
		long currentTime = System.currentTimeMillis();
		long expiryTime = currentTime+tokenValidity;
		return Jwts.builder()
			.setClaims(subject)
			.setIssuedAt(new Date(currentTime))
			.setExpiration(new Date(expiryTime))
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();	
	}
	
	public void validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		}catch(SignatureException e) {
			throw new JwtTokenMalformedException("invalid jwt signature");
		}catch(ExpiredJwtException e) {
			throw new JwtTokenMalformedException("Expired Jwt tokan");
		}catch(IllegalArgumentException e) {
			throw new JwtTokenMissingException("jwt tokan is missing");
		}
	}
	
	public String getSubject(String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return body.getSubject();
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
}
