package com.empresa.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.apirest.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> { // <Entity, Tipo del id>

	//CONSULTAS PERSONALIZADAS JPA
	//Forma 1: JPA Query Languaje
	
	//Forma 2: A través del nombre del método (internamente realiza consulta JPQL 'SELECT u FROM Usuario WHERE u.username=?')
	
	public Usuario findByUsername(String username);
	//Otro ejemplo: public Usuario findByUsernameAndEmail(String username, String email);
	
	/*
	//Forma 3: A través de la anotación
	@Query("select u from Usuario u where u.username=?1") //1: 1 variable
	//otro ejemplo: @Query("select u from Usuario u where u.username=?1 and u.otro=?2")
	public Usuario findByUsername2(String username); 
	*/
	
}
