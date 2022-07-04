package com.empresa.springboot.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.springboot.apirest.models.entity.Factura;
import com.empresa.springboot.apirest.models.entity.Producto;
import com.empresa.springboot.apirest.models.services.IClienteService;

@CrossOrigin(origins= {"http://localhost:4200"}) //CORS
@RestController
@RequestMapping("/api")
public class FacturaRestController {
	
	@Autowired
	private IClienteService clienteService;
	
	/* ---------------------- BUSCAR FACTURAS ----------------------*/
	@Secured({"ROLE_ADMIN","ROLE_USER"}) 
	@GetMapping("/facturas/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Factura show(@PathVariable Long id) {
		return clienteService.findFacturaById(id);
	}
	
	
	/* ---------------------- GUARDAR FACTURAS ----------------------*/
	@Secured({"ROLE_ADMIN"}) 
	@PostMapping("/facturas")
	@ResponseStatus(code=HttpStatus.CREATED)
	public Factura crear(@RequestBody Factura factura) { //Se va a poblar objetos del Json (Binding)
		return clienteService.saveFactura(factura);
	}
	
	/* ---------------------- ELIMINAR FACTURAS ----------------------*/
	@Secured({"ROLE_ADMIN"}) 
	@DeleteMapping("/facturas/{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.deleteFacturaById(id);
	}
	
	
	/* ---------------------- BUSCAR PRODUCTO ----------------------*/
	@Secured({"ROLE_ADMIN"}) 
	@GetMapping("/facturas/filtrar-productos/{term}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Producto> filtrarProducto(@PathVariable String term){
		return clienteService.findProductoByNombre(term);
	}
}
