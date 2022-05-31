package com.empresa.springboot.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.springboot.apirest.models.entity.Cliente;

//CrudRepository: interfaz de SpringDataJPA
//CrudRepository: implementa Transactional, no es necesario @Transactional en sus métodos
public interface IClienteDao extends JpaRepository<Cliente, Long>{
	
}
