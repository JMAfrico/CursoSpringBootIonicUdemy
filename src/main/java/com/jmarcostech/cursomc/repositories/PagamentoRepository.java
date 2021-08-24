package com.jmarcostech.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmarcostech.cursomc.domain.Pagamento;

	@Repository
	public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

	
}
