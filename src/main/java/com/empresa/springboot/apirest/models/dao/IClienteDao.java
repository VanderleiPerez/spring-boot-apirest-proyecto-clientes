package com.empresa.springboot.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.springboot.apirest.models.entity.Cliente;
import com.empresa.springboot.apirest.models.entity.Region;

//CrudRepository: interfaz de SpringDataJPA
//CrudRepository: implementa Transactional, no es necesario @Transactional en sus métodos
public interface IClienteDao extends JpaRepository<Cliente, Long>{
	//Se implementa aquí, porque no hay CRUD para REGIÓN, solo CLIENTES
	//Mapear a consulta JPA - OBJETO
	@Query("from Region") 
	public List<Region> findAllRegiones();
}
