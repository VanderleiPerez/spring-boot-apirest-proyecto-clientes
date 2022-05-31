package com.empresa.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

//Clase de persistencia mapeado a una tabla, cada variable a un campo de la tabla
@Entity
@Table(name="clientes")
public class Cliente implements Serializable{ 

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-increment MySQL
	private Long id;
	
	@Column(nullable = false)
	@NotEmpty (message = "no puede estar vacio, mensaje desde Spring") //depedencia Spring validation - formulario
	@Size (min = 3, max = 12, message = "tiene que estar entre 4 y 12 caracteres, mensaje desde spring") //depedencia Spring validation - formulario
	private String nombre;
	
	@NotEmpty (message = "no puede estar vacio, mensaje desde Spring")//depedencia Spring validation - formulario
	private String apellido;
	@Column(nullable = false, unique = true)
	@NotEmpty (message = "no puede estar vacio, mensaje desde Spring")//depedencia Spring validation - formulario
	@Email (message = "no es una dirección válida de correo, mensaje desde spring")//depedencia Spring validation - formulario
	private String email;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE) //Tipo equivalente en la BD
	private Date createAt;
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	//GETTERS AND SETTERS
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	//ATRIBUTO ESTÁTICO CUANDO SE IMPLEMENTA SERIALIZABLE
	private static final long serialVersionUID = 1L;
	
	
}
