package com.jmarcostech.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriarepository;
	
	public Categoria buscar(Integer id) {	
		Optional<Categoria> cat = categoriarepository.findById(id);
		return cat.orElse(null);
			
	}
}
