package com.jmarcostech.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmarcostech.cursomc.domain.Cliente;
import com.jmarcostech.cursomc.dto.ClienteDTO;
import com.jmarcostech.cursomc.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteservice;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = clienteservice.find(id);
		return ResponseEntity.ok().body(cliente);
		
	}
	
	//REQUEST QUE USA O SERVICE PARA ALTERAR CATEGORIA POR ID
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		Cliente obj = clienteservice.fromDTO(objDTO);
		obj.setId(id);
		obj = clienteservice.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	//REQUEST QUE USA O SERVICE PARA DELETAR CATEGORIA POR ID
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		clienteservice.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS
	/*
	 * @RequestMapping(method = RequestMethod.GET) public
	 * ResponseEntity<List<Cliente>> findAll() { List<Cliente> list =
	 * clienteservice.findAll(); return ResponseEntity.ok().body(list);
	 * 
	 * }
	 */
	
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS, SEM MOSTRAR OS DEPENDENTES
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = clienteservice.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS, SEM MOSTRAR OS DEPENDENTES, POR PAGINAS
	@RequestMapping(value = "/page",method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = clienteservice.findPage(page,linesPerPage,orderBy,direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
		
	}
}
