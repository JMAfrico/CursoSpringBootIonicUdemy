package com.jmarcostech.cursomc.repositories;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmarcostech.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	//Busca cliente por email(recurso de nomes do Spring Data)
	@Transactional(readOnly = true)
	Cliente findByEmail(String email);
}
