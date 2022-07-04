package com.empresa.springboot.apirest.models.services;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.springboot.apirest.models.dao.IUsuarioDao;
import com.empresa.springboot.apirest.models.entity.Usuario;

// UserDetailsService: Interface propia de Spring Security para implementar Login a través de JPA, JDBC, etc
@Service
public class UsuarioService implements UserDetailsService, IUsuarioService{ // IUsuarioService: propio

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly = true) //springframework.transaction.annotation - no de javax.transaction
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		//LOG
		if(usuario==null) {
			logger.error("Error en el login: no existe usuario '"+username+"' en el sistema.");
			throw new UsernameNotFoundException("Error en el login: no existe usuario '"+username+"' en el sistema.");
		}
		//Convertir lista de roles -> lista de SimpleGrantedAuthority
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role->new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: "+authority.getAuthority()))
				.collect(Collectors.toList());
		//authorities: relación ManyToMany
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	} //Interface propia de Spring 

	//Obtener información del usuario
	@Override
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}


	
}
