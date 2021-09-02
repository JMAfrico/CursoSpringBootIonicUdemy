package com.jmarcostech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.domain.Produto;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;
import com.jmarcostech.cursomc.repositories.ProdutoRepository;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtorepository;
	
	@Autowired
	private CategoriaRepository categoriasrepository;
	
	public Produto find(Integer id) {	
		Optional<Produto> cat = produtorepository.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
			
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = categoriasrepository.findAllById(ids);
		//return produtorepository.search(nome, categorias, pageRequest);
		return produtorepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
	
}
