package com.empresa.springboot.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.empresa.springboot.apirest.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
	
}
