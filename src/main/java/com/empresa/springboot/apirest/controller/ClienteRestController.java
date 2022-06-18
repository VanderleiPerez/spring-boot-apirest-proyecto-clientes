package com.empresa.springboot.apirest.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.springboot.apirest.models.entity.Cliente;
import com.empresa.springboot.apirest.models.services.IClienteService;
import com.empresa.springboot.apirest.models.services.UploadFileServiceImp;

//CORS
@CrossOrigin(origins = { "http://localhost:4200" })
//Anotación para API REST
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	// Inyección de dependencia - Listado de clientes desde @Service
	@Autowired
	public IClienteService clienteService;
	// Inyección de dependencia - 
	@Autowired
	public UploadFileServiceImp uploadService;
	
	//Logger: importar de interface slf4j | LoggerFactory: importar de la class
	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
	
	
	/* ---------------------- LISTAR CLIENTE ---------------------- */
	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	/* ---------------------- PAGINAR CLIENTE ---------------------- */
	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 4);
		return clienteService.findAll(pageable); //pagina, tamaño
	}

	/* ---------------------- BUSCAR CLIENTE ---------------------- */
	@GetMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.OK) // status 200
	// ResponseEntity: Clase para manejar error de SPRING
	// -- ?: Tipo genérico porque retornará un Objeto Cliente o un String como
	// mensaje
	public ResponseEntity<?> show(@PathVariable Long id) { // En la URL
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = null;
		// Mensaje de errores en la BD
		try {
			cliente = clienteService.findById(id);
		} catch (DataAccessException e) { // Spring
			response.put("mensaje",
					"El cliente ID: ".concat(id.toString().concat("Error al realizar consulta en la BD")));
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Mensaje de error en caso de no encontrar cliente
		if (cliente == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existen datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);

	}

	/* ---------------------- CREAR CLIENTE ---------------------- */
	@PostMapping("/clientes")
	// @ResponseStatus(HttpStatus.CREATED) // status 201: creado
	// Dependencia validation @valid | BindingResult:Mensaje de error
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) { // Dentro de la
																									// petición (cliente
																									// en json)
		// Mensaje de errores en la BD
		Cliente clienteNew = null;
		Map<String, Object> response = new HashMap<>();

		// Manejo de error de Spring validation
		if (result.hasErrors()) {
			// BadRequest: 400, cuando falla una validación
			/*
			 * OTRA FORMA: List<String> errors = new ArrayList<>(); for(FieldError err:
			 * result.getFieldErrors()) { //iterar errores
			 * errors.add("El campo "+err.getField()+"' '"+err.getDefaultMessage()); }
			 */
			List<String> errors = (List<String>) result.getFieldErrors().stream()
					// Un fieldErrors se convierte a String
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					// Convertir String a Lista
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		try {
			clienteNew = clienteService.save(cliente);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear usuario.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Mensaje de CORRECTO
		response.put("mensaje", "El cliente ha sido creado con éxito");
		response.put("cliente", clienteNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/* ---------------------- ACTUALIZAR CLIENTE ---------------------- */
	@PutMapping("/clientes/{id}")
	// @ResponseStatus(HttpStatus.CREATED) // status 201: creado
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		Cliente clienteActual = clienteService.findById(id);
		Cliente clienteUpdate = null;

		Map<String, Object> response = new HashMap<>();

		// Manejo de error de Spring validation
		if (result.hasErrors()) {
			List<String> errors = (List<String>) result.getFieldErrors().stream()
					// Un fieldErrors se convierte a String
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					// Convertir String a Lista
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		// Mensaje de error en caso de no encontrar cliente
		if (clienteActual == null) {
			response.put("mensaje",
					"Error, no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existen datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			// save hace un INSERT si el id es null, pero si tiene valor el id hace MERGE
			clienteUpdate = clienteService.save(clienteActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Mensaje de CORRECTO
		response.put("mensaje", "El cliente ha sido actualizado con éxito");
		response.put("cliente", clienteUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

		/* ---------------------- ELIMINAR CLIENTE ---------------------- */
	}

	@DeleteMapping("/clientes/{id}")
	// @ResponseStatus(HttpStatus.NO_CONTENT) // status 204
	public ResponseEntity<?> delete(@PathVariable Long id) {
		// No es necesario validar por cliente, CRUD Repository va a verificar que
		// exista el Cliente
		Map<String, Object> response = new HashMap<>();
		try {
			//BORRAR FOTO al eliminar un cliente
			//Obtener cliente
			Cliente cliente = clienteService.findById(id);
			//Al actualizar foto, borrar foto anterior (VALIDACIÓN)
			String nombreFotoAnterior = cliente.getFoto();
		
			//ELIMINAR FOTO - UploadService
			uploadService.eliminar(nombreFotoAnterior);
			
			clienteService.delete(id);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error, al eliminar cliente: ".concat(id.toString().concat(" no existe. ")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Mensaje de CORRECTO
		response.put("mensaje", "El cliente fue eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
		/* ---------------------- SUBIDA DE IMAGEN ---------------------- */
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		//Capturar cliente por id
		Cliente cliente =clienteService.findById(id);
		//Validar imagen
		if(!archivo.isEmpty()) { //si es distinto, copiar la imagen
			
			//COPÍAR FOTO - UploadService
			String nombreArchivo=null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
			response.put("mensaje", "Error al subir la imagen del cliente "+ nombreArchivo) ;
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//Al actualizar foto, borrar foto anterior
			String nombreFotoAnterior = cliente.getFoto();
			//ELIMINAR FOTO - UploadService
			uploadService.eliminar(nombreFotoAnterior);
			
			//Si todo sale bien, guardar 
			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje","Se subió correctamente la imagen "+nombreArchivo);
			
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); 
	}
	
	/* ---------------------- VISUALIZAR IMAGEN ---------------------- */
	
	@GetMapping("/uploads/img/{nombreFoto:.+}") //Expresión regular para incluir . y extensión (jpg,png,etc)
	//Resource importar de springframework.core.io
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		//CARGAR FOTO - UploadService
		Resource recurso = null;
		try {
			recurso = uploadService.carga(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Pasar cabecera de la respuesta, para que la imagen se pueda descargar
		HttpHeaders cabecera = new HttpHeaders();
		//cabecera.add("Content-Disposition", nombreFoto);
		//attachment: fuerza en la respuesta que se descargue imagen
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename()+"\"");
		//Si todo sale bien, se guarda el recurso en la respuesta
		return new ResponseEntity<Resource>(recurso,cabecera, HttpStatus.OK);
	}
}
