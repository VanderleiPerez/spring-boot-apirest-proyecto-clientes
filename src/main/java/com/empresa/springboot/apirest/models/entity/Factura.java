package com.empresa.springboot.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity //clase mapeada
@Table(name="facturas")
public class Factura implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	private String observacion;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE) //formato de fecha en la BD
	private Date createAt; //java.util
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler","facturas"}) //Ignorar relación inversa | evitar loop
	@ManyToOne(fetch = FetchType.LAZY ) //EAGER: | LAZY: Carga perezosa, solo cuando se necesita mediante GET
	private Cliente cliente; // 1 cliente puede tener muchas facturas | 1 factura un solo cliente

	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"},allowSetters = true) //evitar recursión
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL) //Al eliminar una factura, eliminar elementos hijos dependientes
	@JoinColumn(name="factura_id")
	private List<ItemFactura> items;
	

	//Inicializar items como arraylist
	public Factura() {
		items = new ArrayList<>();
	}
	//Hibernate - JPA
	@PrePersist
	public void  prePersist(){//asignar fecha antes de guardar
		this.createAt = new Date();
	}
	
	public Double getTotal(){
		Double total = 0.00;
		for(ItemFactura item: items) {
			total += item.getImporte();
		}
		return total;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemFactura> getItems() {
		return items;
	}
	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
