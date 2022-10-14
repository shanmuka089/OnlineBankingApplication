package com.abc.bankingApp.properties;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abc.bankingApp.common.JwtTokenMissingException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationService authenticationService;
	
	List<String> paths=List.of("/authenticate","/login","/profile/create");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		System.out.println(header);
		if (header == null || !header.startsWith("Bearer")) {
			throw new JwtTokenMissingException("Jwt token not found!..");
		}
		String token = header.substring("Bearer".length() + 1);
		jwtUtil.validateToken(token);
		String userMail = jwtUtil.getSubject(token);
		UserDetails userDetails = authenticationService.loadUserByUsername(userMail);
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails.getUsername(), null, userDetails.getAuthorities());
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return paths.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
	}

}
