package com.jmarcostech.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.repositories.CategoriaRepository;
import com.jmarcostech.cursomc.services.exceptions.DataIntegrityException;
import com.jmarcostech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriarepository;
	
	public Categoria find(Integer id) {	
		Optional<Categoria> cat = categoriarepository.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
			
	}
	
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriarepository.save(obj);
	}
	
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return categoriarepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
		categoriarepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma categoria que contém produtos");
		}
	}
	
}
