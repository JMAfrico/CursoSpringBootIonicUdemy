package com.jmarcostech.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value ="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaservice;
	
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = categoriaservice.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
}
