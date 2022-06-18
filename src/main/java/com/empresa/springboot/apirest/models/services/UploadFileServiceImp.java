package com.empresa.springboot.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//Guardar en el contenedor de Spring , para luego utilizarlo en el controlador
@Service // Representa lógica de negocio
public class UploadFileServiceImp implements IUploadFileService {

	// Logger: importar de interface slf4j | LoggerFactory: importar de la class
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImp.class);

	private final static String DIRECTORIO_UPLOAD = "uploads";

	/* ---------------------- CARGAR FOTO ---------------------- */

	@Override
	public Resource carga(String nombreFoto) throws MalformedURLException {
		// Ruta
		Path rutaArchivo = getPath(nombreFoto);
		log.info(rutaArchivo.toString());

		// Creación del recurso (TRY CATCH)
		Resource recurso = new UrlResource(rutaArchivo.toUri());

		// si existe y se puede leer
		if (!recurso.exists() && !recurso.isReadable()) {
			// Agregar imagen por defecto, en caso no se detecter una imagen
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("imgUser.png").toAbsolutePath();
			// creación del recurso (TRY CATCH)
			recurso = new UrlResource(rutaArchivo.toUri());

			// throw new RuntimeException("Error, no se pudo cargar la imagen " +
			// nombreFoto); -- se guardará en el LOG
			log.error("Error, no se pudo cargar la imagen " + nombreFoto);
		}
		return recurso;
	}

	/* ---------------------- COPIAR FOTO ---------------------- */
	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString()+"_"+archivo.getOriginalFilename().replace(" ", ""); //nombre original del archivo
		Path rutaArchivo = getPath(nombreArchivo);
		//LOG
		log.info(rutaArchivo.toString());
		//copiar el archivo subido al servidor a la ruta seleccionada (TRY CATCH)
			Files.copy(archivo.getInputStream(), rutaArchivo);
	
		return nombreArchivo;
	}

	/* ---------------------- ELIMINAR FOTO ---------------------- */
	@Override
	public boolean eliminar(String nombreFoto) {
		if(nombreFoto !=null && nombreFoto.length()>0) {
			//Obtener la ruta y el nombre de la imagen
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			//convertir archivo a un tipo File
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}
		return false;
	}
	/* ---------------------- PATH FOTO ---------------------- */
	@Override
	public Path getPath(String nombreFoto) {
		// TODO Auto-generated method stub
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
