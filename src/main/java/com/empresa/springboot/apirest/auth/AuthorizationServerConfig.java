package com.empresa.springboot.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired // AuthorizationServer se utilizará para el login 
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private InfoAdicionalToken infoAdicionalToken;
	
	/* --- MÉTODOS DE CONFIGURACIÓN --- */ 
	
	//MÉTODO: Configurar permisos
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		security.tokenKeyAccess("permitAll()") //permiso a cualquier usuario para que se pueda autenticar en /oauth/token
		.checkTokenAccess("isAuthenticated()"); // Enviar token en las cabeceras: dar acceso
	}

	//MÉTODO: Registrar clientes  
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp")
		.secret(passwordEncoder.encode("12345"))
		.scopes("read","write")
		.authorizedGrantTypes("password","refresh_token") //tipo de concesión del token: ¿Cómo se obtiene el token? cuando usuarios existen en el backend enviar credenciales (appcliente | credenciales del usuario)
		.accessTokenValiditySeconds(3600) //tiempo de caducidad
		.refreshTokenValiditySeconds(3600); //Obtener nuevo token sin tener que iniciar sesión
	}
	//MÉTODO: Autenticación y validación del token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//registrar componente en servidor de autorización (JWT+info)
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		//Registrar AuthorizationManager | Registrar accessToken
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter()) //Almacena datos del usuario(no sensible) | Traduce datos codificados del usuario en el JWT, para que AutheticationManager lo pueda validar
		.tokenEnhancer(tokenEnhancerChain);
	}
	@Bean
	public JwtTokenStore tokenStore() {
		// TODO Auto-generated method stub
		return new JwtTokenStore(accessTokenConverter());
	}

	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		//Llave de verificación por defecto cambiado 
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//Con la llave secreta se crea token en el servidor de autorización y cada vez que el cliente envia el token al servidor de recursos, lo valida a través del servidor de autorización
		//jwtAccessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA);
		return jwtAccessTokenConverter;
	}
			
}
