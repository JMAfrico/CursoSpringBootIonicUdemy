package com.jmarcostech.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;
import com.jmarcostech.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value ="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaservice;
	private CategoriaRepository categoriarepository;
		
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = categoriaservice.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = categoriaservice.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	

}
