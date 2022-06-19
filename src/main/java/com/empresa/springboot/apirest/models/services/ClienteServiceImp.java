package com.empresa.springboot.apirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.empresa.springboot.apirest.models.dao.IClienteDao;
import com.empresa.springboot.apirest.models.entity.Cliente;
import com.empresa.springboot.apirest.models.entity.Region;

//Decorar la clase como componente de servicios. Además se almacena en el contenedor de Spring
@Service
public class ClienteServiceImp implements IClienteService{

	//Inyecciónd de dependencia (Guarda en el contenedor de Spring)
	@Autowired
	private IClienteDao clienteDao;

	/* ---------------------- LISTAR CLIENTE ----------------------*/ 
	@Override
	@Transactional(readOnly = true) //Solo de lectura
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}
	/* ---------------------- PAGINAR CLIENTE ----------------------*/ 
	@Override
	@Transactional(readOnly = true) //Solo de lectura

	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}
	/* ---------------------- BUSCAR CLIENTE ----------------------*/ 
	@Override
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	/* ---------------------- CREAR CLIENTE ----------------------*/ 
	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}
	
	/* ---------------------- ELIMINAR CLIENTE ----------------------*/ 
	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}
	
	/* ---------------------- LISTAR REGIONES ----------------------*/ 
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		// TODO Auto-generated method stub
		return clienteDao.findAllRegiones();
	}


}
