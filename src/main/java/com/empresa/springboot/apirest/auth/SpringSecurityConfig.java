package com.empresa.springboot.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

@EnableGlobalMethodSecurity(securedEnabled = true) //Habilitar anotaciones de seguridad en el controlador
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserDetailsService usuarioService;

	@Bean //Objeto que retorna el método lo registra
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); //retornar instancia del beans
	}
	
	@Override
	@Autowired //para el componente de Spring : AuthenticationManagerBuilder
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder()); //encriptador
	}

	// AuthenticationManager
	@Bean("authenticationManager") //por defecto es el mismo nombre del método
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	/* ------ Seguridad spring: extraido de ResourceServerConfig ------ */
	//CSRF: Falsificación de petición | proteger formulario por token | deshabilitar porque se trabaja por separado con Angular
	@Override
	public void configure(HttpSecurity http) throws Exception {
	
		http.authorizeRequests()
		.anyRequest().authenticated() //cualquier otra página es privada | añadir a toda ruta sin permisos
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //stateless porque se trabaja API REST | para MVC se usa stateful
		
	}
	
}
