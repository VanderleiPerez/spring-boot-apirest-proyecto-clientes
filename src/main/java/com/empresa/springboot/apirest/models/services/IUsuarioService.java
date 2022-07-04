package com.empresa.springboot.apirest.models.services;

import com.empresa.springboot.apirest.models.entity.Usuario;

public interface IUsuarioService {
	public Usuario findByUsername(String username);

}
