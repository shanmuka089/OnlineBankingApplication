package com.abc.bankingApp.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig{
	 
		@Autowired
		private JwtAuthenticationFilter authenticationFilter;
	
		@Autowired
	    private AuthenticationService authenticationService;
	 
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	        .cors().and()
	        .csrf().disable()
	        .authorizeRequests().antMatchers("/authenticate","/login","/profile/create").permitAll()
	        .anyRequest().authenticated().and().addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
	        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        return http.build();
	    }
	    
//	    @Bean
//	    public WebSecurityCustomizer webSecurityCustomizer() {
//	        return (web) -> web.ignoring().antMatchers("/authenticate","/login","/profile/create");
//	    }
	    
	    
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(authenticationService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
}
