package com.empresa.springboot.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Serializable para guardar en la sesión HTTP y trabajar con Json
@Entity // JPA
@Table(name = "regiones")
public class Region implements Serializable {
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Identity: MySQL,SQLServer | SEQUENCE: Oracle,PG
	private Long id;
	private String nombre;

	// GETTERS - SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// VALOR SERIALIZABLE
	private static final long serialVersionUID = 1L;

}
