package com.jmarcostech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jmarcostech.cursomc.domain.Categoria;
import com.jmarcostech.cursomc.dto.CategoriaDTO;
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
		Categoria newObj = find(obj.getId());
		updateData(newObj , obj);
		return categoriarepository.save(newObj);
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
	
	//SERVICE PARA PAGINAR AS CATEGORIAS
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return categoriarepository.findAll(pageRequest);
	}
	
	//SERVICE QUE PASSA OS PARÂMETROS DA CATEGORIA, PARA CATEGORIADTO, PARA PODER SER VALIDADO PELO REQUEST
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
	
	//SERVICE QUE PUXA OS DADOS ORIGINAIS DO BANCO QUE PODEM SER ALTERADOS
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());

	}
}
