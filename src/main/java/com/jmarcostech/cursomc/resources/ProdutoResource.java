package com.jmarcostech.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmarcostech.cursomc.domain.Produto;
import com.jmarcostech.cursomc.dto.ProdutoDTO;
import com.jmarcostech.cursomc.resources.utils.URL;
import com.jmarcostech.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value ="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoservice;
		
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = produtoservice.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	//REQUEST QUE USA O SERVICE PARA LISTAR TODAS AS CATEGORIAS, SEM MOSTRAR OS DEPENDENTES, POR PAGINAS
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.DecodeParam(nome);
		List<Integer> ids = URL.DecoderIntList(categorias);
		
		Page<Produto> list = produtoservice.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
		
	}
}
