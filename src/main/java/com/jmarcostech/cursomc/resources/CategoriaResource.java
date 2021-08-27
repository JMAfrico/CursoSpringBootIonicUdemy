package com.jmarcostech.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.dto.CategoriaDTO;
import com.jmarcostech.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value ="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaservice;
	//private CategoriaRepository categoriarepository;
		
	//REQUEST QUE USA O SERVICE PARA BUSCAR CATEGORIA POR ID
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = categoriaservice.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	//REQUEST QUE USA O SERVICE PARA SALVAR NOVA CATEGORIA
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = categoriaservice.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(obj.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//REQUEST QUE USA O SERVICE PARA ALTERAR CATEGORIA POR ID
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id){
		categoria.setId(id);
		categoria = categoriaservice.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	//REQUEST QUE USA O SERVICE PARA DELETAR CATEGORIA POR ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaservice.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS
	/*
	 * @RequestMapping(method = RequestMethod.GET) public
	 * ResponseEntity<List<Categoria>> findAll() { List<Categoria> list =
	 * categoriaservice.findAll(); return ResponseEntity.ok().body(list);
	 * 
	 * }
	 */
	
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS, SEM MOSTRAR OS DEPENDENTES
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = categoriaservice.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	

}
