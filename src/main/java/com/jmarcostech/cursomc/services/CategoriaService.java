package com.jmarcostech.cursomc.services;

import java.util.List;
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
	
	//SERVICE PARA BUSCAR CATEGORIA POR ID
	public Categoria find(Integer id) {	
		Optional<Categoria> cat = categoriarepository.findById(id);
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));			
	}
	
	//SERVICE PARA INSERIR CATEGORIA NOVA, SE ELA JÁ NÃO EXISTIR
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriarepository.save(obj);
	}
	
	//SERVICE PARA ALTERAR CATEGORIA SE ELA JÁ EXISTIR
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return categoriarepository.save(obj);
	}
	
	//SERVICE PARA DELETAR CATEGORIA SE ELA JÁ EXISTIR
	public void delete(Integer id) {
		find(id);
		try {
		categoriarepository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma categoria que contém produtos");
		}
	}
	
	//SERVICE PARA LISTAR TODAS AS CATEGORIAS
	public List<Categoria> findAll() {
		return categoriarepository.findAll();
	}
	
}
