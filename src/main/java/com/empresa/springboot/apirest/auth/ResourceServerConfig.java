package com.empresa.springboot.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableResourceServer //Habilitar servidor de recursos
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	/* ------ Seguridad oauth2 ------ */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/**","/api/uploads/img/**","/images/**")
		.permitAll() //permitir a todos los usuarios
		//.antMatchers("/api/clientes/{id}").permitAll() // solo desactivado durante la creación de factura,producto, etc
		//.antMatchers("/api/facturas/**").permitAll() // solo desactivado durante la creación de factura, producto,etc

		/* Se cambió roles a anotaciones
		.antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("USER","ADMIN") //Imagenes
		.antMatchers(HttpMethod.POST, "api/clientes/").hasRole("ADMIN")
		.antMatchers("/api/clientes/**").hasAnyRole("ADMIN") //**: cualquier ruta hacia adelante requiere permiso de admin | incluido listar regiones
		*/
		.anyRequest().authenticated() //cualquier otra página es privada | añadir autenticación a toda ruta sin permiso 
		.and().cors().configurationSource(corsConfigurationSource()); // 
	}
	
	//Configuración de CORS
	@Bean
	CorsConfigurationSource corsConfigurationSource() { //importar de .cors
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); //Configurar cors (permitir el dominio)
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS")); //Métodos permitidos | Options: Algunos navegadores para solicitar el token, el request es Option | Opcional usar * para todos los métodos
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Content-Type","Authorization","Content-Length","Host"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //Registrar configuración del CORS para todas rutas del backend

		source.registerCorsConfiguration("/**", configuration);
		return  source;
	}
	//Filtro de CORS, registrandolo en del conjunto de filtros de Spring
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){ //CorsFilter: importar de springframework
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); //mayor prioridad
		return bean;
	
	
	}

}
